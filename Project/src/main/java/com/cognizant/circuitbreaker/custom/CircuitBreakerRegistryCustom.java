package com.cognizant.circuitbreaker.custom;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cognizant.circuitbreaker.config.CircuitBreakerConfigTask;

import io.github.resilience4j.core.ConfigurationNotFoundException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CircuitBreakerRegistryCustom {

	public static final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
	
	private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	private static final String ELB_ATTACH_DETACH = "sre-failover-c-breaker-elb-attach-detach";

	private static final String CSR_ZERO_LOADER_TOPIC = "csr-zero-out-loaders-sns-topic";

	private static final String ALARM_TOPIC = "AlarmProcessedTopic";

	private static final String CONFIG_FORMAT = "%s";

	private static final String DEFAULT = "cogdefault";
	
	private static List<String> topicARNList = new ArrayList<>();
	
	static {
		topicARNList.add(ELB_ATTACH_DETACH);
		topicARNList.add(CSR_ZERO_LOADER_TOPIC);
		topicARNList.add(ALARM_TOPIC);
		buildBreakers();
		
	}
	
	static void buildBreakers() {
		
		 String defaultBreakerName = DEFAULT;
		 System.out.println("*****CircuitBreakerRegistryCustom*****buildBreakers***");
		final CircuitBreakerConfig defaultCircuitBreakerConfig = getDefaultCircuitBreakerConfig();
//		 final CircuitBreakerConfigTask defaultCircuitBreakerConfig = new CircuitBreakerConfigTask();
						
		circuitBreakerRegistry.getEventPublisher().onEntryAdded(entryAddedEvent -> {
            CircuitBreaker addedCircuitBreaker = entryAddedEvent.getAddedEntry();
       });
		
		final String configName = String.format(CONFIG_FORMAT, defaultBreakerName);
        circuitBreakerRegistry.addConfiguration(configName, defaultCircuitBreakerConfig);
		executorService.scheduleAtFixedRate(new CircuitBreakerConfigTask(), 0, 1, TimeUnit.MINUTES);
		  
		
	}
	
	public static CircuitBreaker getCircuitBreaker(final String breakerName) {

        CircuitBreaker circuitBreaker;
        try {
            circuitBreaker = circuitBreakerRegistry.circuitBreaker(breakerName,
                    String.format(CONFIG_FORMAT, breakerName));
        } catch (ConfigurationNotFoundException e) {
            circuitBreaker = circuitBreakerRegistry.circuitBreaker(breakerName,
                    String.format(CONFIG_FORMAT, DEFAULT));

            // if config not found, use the default config and make sure it's transitioned
            // it to disabled
            // updating to try catch block, if block led to race condition, if block was
            // still allowing a disabled CB to
            // be disabled again, expected TOCTU race condition
            try {
                circuitBreaker.transitionToDisabledState();
            } catch (Exception illegalState) {
                
                // double check default CB is disabled, the throw block should never activate
                if (circuitBreaker.getState() != CircuitBreaker.State.DISABLED) {
                    throw illegalState;
                }
            }

         }
        return circuitBreaker;
    }
	

	private static CircuitBreakerConfig getDefaultCircuitBreakerConfig() {
        return CircuitBreakerConfig.from(circuitBreakerRegistry.getDefaultConfig()).failureRateThreshold(80)
                .slowCallRateThreshold(80).waitDurationInOpenState(Duration.ofSeconds(10))
                .slowCallDurationThreshold(Duration.ofSeconds(60)).permittedNumberOfCallsInHalfOpenState(100)
                .minimumNumberOfCalls(100).slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .slidingWindowSize(10).ignoreExceptions(CancellationException.class)
                .build();
    }

	

}

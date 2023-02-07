package com.cognizant.circuitbreaker.config;

import com.cognizant.circuitbreaker.custom.CircuitBreakerRegistryCustom;
import com.cognizant.circuitbreaker.model.CircuitBreakerConfigModel;
import com.cognizant.circuitbreaker.repository.CircuitBreakerConfigRepository;
import com.cognizant.circuitbreaker.exception.*;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CircuitBreakerConfigTask implements Runnable {
	
	private static final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistryCustom.circuitBreakerRegistry;
	private static final Map<String, Integer> configVersionMap = new HashMap<>();
	public static final String CONFIG_FORMAT = "%s";
	private static final Duration ACCEPTABLE_REFRESH_PERIOD = Duration.ofNanos(1L);;
	public void run() {
		// TODO Auto-generated method stub
		try {
			CheckForUpdates();
			logMetrics();
		}catch(IllegalArgumentException e)
		{System.out.println("*****CircuitBreakerConfigTask*****run***IllegalArgumentException**"+e);}
		catch(Throwable e) {}
		
	}

	private void logMetrics() {
		
		for(CircuitBreaker circuitBreaker : circuitBreakerRegistry.getAllCircuitBreakers())
		{
			final CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
		}
		
	}

	private void CheckForUpdates() {
		System.out.println("*****CircuitBreakerConfigTask*****CheckForUpdates***");
		final List<CircuitBreakerConfigModel> circuitBreakerConfigs =
                CircuitBreakerConfigRepository.getCircuitBreakerVersions();
		System.out.println("*****CircuitBreakerConfigTask*****circuitBreakerConfigs***"+circuitBreakerConfigs.toString());
        for (final CircuitBreakerConfigModel config : circuitBreakerConfigs) {

            final String target = config.getTarget();
            System.out.println("*****CircuitBreakerConfigTask*****CheckForUpdates***target***"+target);
            if (configVersionMap.containsKey(target)) {
                final Integer version = configVersionMap.get(target);

                if (config.getVersion() > version) {
                    addConfiguration(target);
                }
            } else {
                addConfiguration(target);
            }
        }
    
	}

	private void addConfiguration(String target) {
		// TODO Auto-generated method stub
		final CircuitBreakerConfigModel updatedModel = CircuitBreakerConfigRepository.getCircuitBreakerConfig(target);
		
		final CircuitBreakerConfig.Builder defaultConfig = CircuitBreakerConfig.from(circuitBreakerRegistry.getDefaultConfig());
		
		final CircuitBreakerConfig customConfig  = applyConfigurations(updatedModel, defaultConfig);
		
		circuitBreakerRegistry.addConfiguration(String.format(CONFIG_FORMAT, target), customConfig);
		
		circuitBreakerRegistry.remove(target);
		
		configVersionMap.put(target, updatedModel.getVersion());
	}

	private static CircuitBreakerConfig applyConfigurations(CircuitBreakerConfigModel config, CircuitBreakerConfig.Builder customConfig) {
				
		int failureRateThreshold = config.getFailureRateThreshold();
        int slowCallRateThreshold = config.getSlowCallRateThreshold();
        int waitDurationInOpenState = config.getWaitDurationInOpenState();
        int slowCallDurationThreshold = config.getSlowCallDurationThreshold();
        int permittedNumberOfCallsInHalfOpenState = config.getPermittedNumberOfCallsInHalfOpenState();
        int minimumNumberOfCalls = config.getMinimumNumberOfCalls();
        int slidingWindowSize = config.getSlidingWindowSize();

        if (failureRateThreshold > 0) {
            customConfig.failureRateThreshold(failureRateThreshold);
        }
        if (slowCallRateThreshold > 0) {
            customConfig.slowCallRateThreshold(slowCallRateThreshold);
        }
        if (waitDurationInOpenState > 0) {
            customConfig.waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState));
        }
        if (slowCallDurationThreshold > 0) {
            customConfig.slowCallDurationThreshold(Duration.ofMillis(slowCallDurationThreshold));
        }
        if (permittedNumberOfCallsInHalfOpenState > 0) {
            customConfig.permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState);
        }
        if (minimumNumberOfCalls > 0) {
            customConfig.minimumNumberOfCalls(minimumNumberOfCalls);
        }
        if (slidingWindowSize > 0) {
            customConfig.slidingWindowSize(slidingWindowSize);
        }

        customConfig.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED);

		/*
		 * customConfig.recordExceptions(HttpException.class, IOException.class,
		 * ResilientRsfHttpException.class, ResilientURLConnectionException.class,
		 * ResilientObjectException.class);
		 */

        return customConfig.build();
    
	}
	
	

}

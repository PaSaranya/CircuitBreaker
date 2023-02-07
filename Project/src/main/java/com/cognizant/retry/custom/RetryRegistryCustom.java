package com.cognizant.retry.custom;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cognizant.circuitbreaker.config.CircuitBreakerConfigTask;
import com.cognizant.retry.config.RetryConfigTask;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.core.ConfigurationNotFoundException;

public class RetryRegistryCustom {
	/*
	 * 
	 * public static final RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
	 * 
	 * private static final ScheduledExecutorService executorService =
	 * Executors.newScheduledThreadPool(1);
	 * 
	 * private static final String ELB_ATTACH_DETACH =
	 * "sre-failover-c-breaker-elb-attach-detach";
	 * 
	 * private static final String CSR_ZERO_LOADER_TOPIC =
	 * "csr-zero-out-loaders-sns-topic";
	 * 
	 * private static final String ALARM_TOPIC = "AlarmProcessedTopic";
	 * 
	 * private static final String CONFIG_FORMAT = "%s_config";
	 * 
	 * private static final String DEFAULT = "default";
	 * 
	 * private static List<String> topicARNList = new ArrayList<>();
	 * 
	 * static { topicARNList.add(ELB_ATTACH_DETACH);
	 * topicARNList.add(CSR_ZERO_LOADER_TOPIC); topicARNList.add(ALARM_TOPIC);
	 * buildBreakers();
	 * 
	 * }
	 * 
	 * static void buildBreakers() {
	 * 
	 * String defaultBreakerName = DEFAULT;
	 * 
	 * final RetryConfig defaultRetryConfig = getDefaultRetryConfig();
	 * 
	 * retryRegistry.getEventPublisher().onEntryAdded(entryAddedEvent -> { Retry
	 * addedRetry = entryAddedEvent.getAddedEntry(); });
	 * 
	 * final String configName = String.format(CONFIG_FORMAT, defaultBreakerName);
	 * retryRegistry.addConfiguration(configName, defaultRetryConfig);
	 * executorService.scheduleAtFixedRate(new RetryConfigTask(), 0, 1,
	 * TimeUnit.MINUTES);
	 * 
	 * 
	 * }
	 * 
	 * public static Retry getRetry(final String breakerName) {
	 * 
	 * Retry retry; try { retry = retryRegistry.circuitBreaker(breakerName,
	 * String.format(CONFIG_FORMAT, breakerName)); } catch
	 * (ConfigurationNotFoundException e) { retry =
	 * retryRegistry.circuitBreaker(breakerName, String.format(CONFIG_FORMAT,
	 * DEFAULT));
	 * 
	 * // if config not found, use the default config and make sure it's
	 * transitioned // it to disabled // updating to try catch block, if block led
	 * to race condition, if block was // still allowing a disabled CB to // be
	 * disabled again, expected TOCTU race condition try {
	 * retry.transitionToDisabledState(); } catch (Exception illegalState) {
	 * 
	 * // double check default CB is disabled, the throw block should never activate
	 * if (retry.getState() != CircuitBreaker.State.DISABLED) { throw illegalState;
	 * } }
	 * 
	 * } return retry; }
	 * 
	 * 
	 * private static CircuitBreakerConfig getDefaultCircuitBreakerConfig() { return
	 * CircuitBreakerConfig.from(circuitBreakerRegistry.getDefaultConfig()).
	 * failureRateThreshold(80)
	 * .slowCallRateThreshold(80).waitDurationInOpenState(Duration.ofSeconds(10))
	 * .slowCallDurationThreshold(Duration.ofSeconds(60)).
	 * permittedNumberOfCallsInHalfOpenState(100)
	 * .minimumNumberOfCalls(100).slidingWindowType(CircuitBreakerConfig.
	 * SlidingWindowType.TIME_BASED)
	 * .slidingWindowSize(10).ignoreExceptions(CancellationException.class)
	 * .build(); }
	 * 
	 * 
	 * 
	 */}

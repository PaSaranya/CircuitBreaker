package com.cognizant.retry.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cognizant.circuitbreaker.custom.CircuitBreakerRegistryCustom;
import com.cognizant.circuitbreaker.model.CircuitBreakerConfigModel;
import com.cognizant.circuitbreaker.repository.CircuitBreakerConfigRepository;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

public class RetryConfigTask implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * 
	 * private static final RetryRegistry retryRegistry =
	 * RetryRegistryCustom.retryRegistry; private static final Map<String, Integer>
	 * configVersionMap = new HashMap<>(); public static final String CONFIG_FORMAT
	 * = "%s_config"; private static final Duration ACCEPTABLE_REFRESH_PERIOD =
	 * Duration.ofNanos(1L);; public void run() { // TODO Auto-generated method stub
	 * try { CheckForUpdates(); logMetrics(); }catch(IllegalArgumentException e) {}
	 * catch(Throwable e) {}
	 * 
	 * }
	 * 
	 * private void logMetrics() {
	 * 
	 * for(Retry retry : retryRegistry.getAllCircuitBreakers()) { final
	 * Retry.Metrics metrics = retry.getMetrics(); }
	 * 
	 * }
	 * 
	 * private void CheckForUpdates() {
	 * 
	 * final List<RetryConfigModel> retryConfigs =
	 * RetryConfigRepository.getCircuitBreakerVersions();
	 * 
	 * for (final RetryConfigModel config : retryConfigs) {
	 * 
	 * final String target = config.getTarget();
	 * 
	 * if (configVersionMap.containsKey(target)) { final Integer version =
	 * configVersionMap.get(target);
	 * 
	 * if (config.getVersion() > version) { addConfiguration(target); } } else {
	 * addConfiguration(target); } }
	 * 
	 * }
	 * 
	 * private void addConfiguration(String target) {
	 * 
	 * final RetryConfigModel updatedModel =
	 * RetryConfigRepository.getCircuitBreakerConfig(target);
	 * 
	 * final RetryConfig.Builder defaultConfig =
	 * RetryConfig.from(retryRegistry.getDefaultConfig());
	 * 
	 * final RetryConfig customConfig = applyConfigurations(updatedModel,
	 * defaultConfig);
	 * 
	 * retryRegistry.addConfiguration(String.format(CONFIG_FORMAT, target),
	 * customConfig);
	 * 
	 * retryRegistry.remove(target);
	 * 
	 * configVersionMap.put(target, updatedModel.getVersion()); }
	 * 
	 * private static RetryConfig applyConfigurations(RetryConfigModel config,
	 * RetryConfig.Builder customConfig) {
	 * 
	 * int failureRateThreshold = config.getFailureRateThreshold(); int
	 * slowCallRateThreshold = config.getSlowCallRateThreshold(); int
	 * waitDurationInOpenState = config.getWaitDurationInOpenState(); int
	 * slowCallDurationThreshold = config.getSlowCallDurationThreshold(); int
	 * permittedNumberOfCallsInHalfOpenState =
	 * config.getPermittedNumberOfCallsInHalfOpenState(); int minimumNumberOfCalls =
	 * config.getMinimumNumberOfCalls(); int slidingWindowSize =
	 * config.getSlidingWindowSize();
	 * 
	 * if (failureRateThreshold > 0) {
	 * customConfig.failureRateThreshold(failureRateThreshold); } if
	 * (slowCallRateThreshold > 0) {
	 * customConfig.slowCallRateThreshold(slowCallRateThreshold); } if
	 * (waitDurationInOpenState > 0) {
	 * customConfig.waitDurationInOpenState(Duration.ofMillis(
	 * waitDurationInOpenState)); } if (slowCallDurationThreshold > 0) {
	 * customConfig.slowCallDurationThreshold(Duration.ofMillis(
	 * slowCallDurationThreshold)); } if (permittedNumberOfCallsInHalfOpenState > 0)
	 * { customConfig.permittedNumberOfCallsInHalfOpenState(
	 * permittedNumberOfCallsInHalfOpenState); } if (minimumNumberOfCalls > 0) {
	 * customConfig.minimumNumberOfCalls(minimumNumberOfCalls); } if
	 * (slidingWindowSize > 0) { customConfig.slidingWindowSize(slidingWindowSize);
	 * }
	 * 
	 * customConfig.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.
	 * TIME_BASED);
	 * 
	 * 
	 * customConfig.recordExceptions(HttpException.class, IOException.class,
	 * ResilientRsfHttpException.class, ResilientURLConnectionException.class,
	 * ResilientObjectException.class);
	 * 
	 * 
	 * return customConfig.build();
	 * 
	 * }
	 * 
	 * 
	 * 
	 */}

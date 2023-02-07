package com.cognizant.circuitbreaker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.time.Duration;

@DynamoDBTable(tableName="sre-circuit-breaker-config")
public class CircuitBreakerConfigModel {


    public static final String EMPTY_STRING = "";

    private String key = EMPTY_STRING;
    private String target = EMPTY_STRING;
    private int version;
    private int failureRateThreshold;
    private int slowCallRateThreshold;
    private int waitDurationInOpenState;
    private int slowCallDurationThreshold;
    private int permittedNumberOfCallsInHalfOpenState;
    private int minimumNumberOfCalls;
    private int slidingWindowSize;

    @DynamoDBHashKey(attributeName = "breakerKey")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @DynamoDBRangeKey(attributeName = "breakerTarget")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @DynamoDBAttribute(attributeName = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @DynamoDBAttribute(attributeName = "failureRateThreshold")
    public int getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(int failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    @DynamoDBAttribute(attributeName = "slowCallRateThreshold")
    public int getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(int slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    @DynamoDBAttribute(attributeName = "waitDurationInOpenState")
    public int getWaitDurationInOpenState() {
        return waitDurationInOpenState;
    }

    public void setWaitDurationInOpenState(int waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
    }

    @DynamoDBAttribute(attributeName = "slowCallDurationThreshold")
    public int getSlowCallDurationThreshold() {
        return slowCallDurationThreshold;
    }

    public void setSlowCallDurationThreshold(int slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
    }

    @DynamoDBAttribute(attributeName = "permittedNumberOfCallsInHalfOpenState")
    public int getPermittedNumberOfCallsInHalfOpenState() {
        return permittedNumberOfCallsInHalfOpenState;
    }

    public void setPermittedNumberOfCallsInHalfOpenState(int permittedNumberOfCallsInHalfOpenState) {
        this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
    }

    @DynamoDBAttribute(attributeName = "minimumNumberOfCalls")
    public int getMinimumNumberOfCalls() {
        return minimumNumberOfCalls;
    }

    public void setMinimumNumberOfCalls(int minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
    }

    @DynamoDBAttribute(attributeName = "slidingWindowSize")
    public int getSlidingWindowSize() {
        return slidingWindowSize;
    }

    public void setSlidingWindowSize(int slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
    }

    @Override
    public String toString() {
        return "CircuitBreakerConfigModel{" +
                "key='" + key + '\'' +
                ", target='" + target + '\'' +
                ", version=" + version +
                ", failureRateThreshold=" + failureRateThreshold +
                ", slowCallRateThreshold=" + slowCallRateThreshold +
                ", waitDurationInOpenState=" + waitDurationInOpenState +
                ", slowCallDurationThreshold=" + slowCallDurationThreshold +
                ", permittedNumberOfCallsInHalfOpenState=" + permittedNumberOfCallsInHalfOpenState +
                ", minimumNumberOfCalls=" + minimumNumberOfCalls +
                ", slidingWindowSize=" + slidingWindowSize +
                '}';
    }

}

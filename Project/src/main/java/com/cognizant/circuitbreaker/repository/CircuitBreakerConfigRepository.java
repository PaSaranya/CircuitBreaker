package com.cognizant.circuitbreaker.repository;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.AbstractDynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.cognizant.circuitbreaker.aws.DynamoDBClientFactory;
import com.cognizant.circuitbreaker.model.CircuitBreakerConfigModel;
public class CircuitBreakerConfigRepository {
	
	private static final String rateLimiterFormat = "%s:%s:%s";
	private static String rateLimiterKey = null;
	private static final String PROJECTION_EXPRESSION = "breakerKey, breakerTarget, failureRateThreshold, minimumNumberOfCalls, permittedNumberOfCallsInHalfOpenState, slidingWindowSize, slowCallDurationThreshold, slowCallRateThreshold, version, waitDurationInOpenState";
	private static DynamoDBMapper dynamoDBMapper;
	
	public static List<CircuitBreakerConfigModel> getCircuitBreakerVersions()
	{
		final CircuitBreakerConfigModel circuitBreakerConfigModel=new CircuitBreakerConfigModel();
		circuitBreakerConfigModel.setKey(getBreakerKey());
		
		final DynamoDBQueryExpression<CircuitBreakerConfigModel> queryExpression = 
				new DynamoDBQueryExpression().withHashKeyValues(circuitBreakerConfigModel)
				                             .withProjectionExpression(PROJECTION_EXPRESSION);
		
		return getDynamoDBMapper().query(CircuitBreakerConfigModel.class,queryExpression);
		
		
	}

	private static DynamoDBMapper getDynamoDBMapper() {
		
		System.out.println("*****CircuitBreakerConfigRepository*****getDynamoDBMapper***");
		if(dynamoDBMapper==null)
		{
			final AmazonDynamoDB client = new DynamoDBClientFactory().getAmazonDynamoDBClient();
			dynamoDBMapper = new DynamoDBMapper(client);
		}
		return dynamoDBMapper;
	}

	private static String getBreakerKey() {
		
		// TODO Auto-generated method stub
		if(rateLimiterKey==null)
		{
			
			final String assetAreaName = "cert";
			final String envName = "search";
			final String assetName = "circuitbreaker"; 
			
			rateLimiterKey = String.format(rateLimiterFormat, envName, assetName, assetAreaName);
			rateLimiterKey = "javatpoint.com";
			
		}
		return rateLimiterKey;
	}
	
	public static void setCircuitBreakerConfigKey(String envName, String assetName, String assetAreaName)
	{
		rateLimiterKey = String.format(rateLimiterFormat, envName, assetName, assetAreaName);
	}
	
	public static CircuitBreakerConfigModel getCircuitBreakerConfig(String target)
	{
		final CircuitBreakerConfigModel configModel = getDynamoDBMapper().load(CircuitBreakerConfigModel.class, getBreakerKey(), target);
		return configModel;
	}

}

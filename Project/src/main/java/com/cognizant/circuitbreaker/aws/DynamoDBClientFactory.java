package com.cognizant.circuitbreaker.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
public class DynamoDBClientFactory {
	
	private static final String AWS_CREDENTIALS_FILE_PATH = "";
	
	public AmazonDynamoDB getAmazonDynamoDBClient()
	{
		System.out.println("*****DynamoDBClientFactory*****getAmazonDynamoDBClient***");
		if(AWS_CREDENTIALS_FILE_PATH !=null)
		{
			/*
			 * final AWSCredentialsProvider credentialsProvider = new
			 * AmazonCredentialsFactory().getAmazonCredentials(); BasicAWSCredentials
			 * awsCreds = new BasicAWSCredentials("AKIARQM26YNZ3SFO6NXZ",
			 * "S9PzFkOlceH7HAAbVkJBZvL/wndjoHEFIL28ZSyo"); return
			 * AmazonDynamoDBClientBuilder.standard().withCredentials(new
			 * AWSStaticCredentialsProvider(awsCreds)).build();
			 */
			
			AWSCredentialsProvider creds= new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIARQM26YNZ3SFO6NXZ","S9PzFkOlceH7HAAbVkJBZvL/wndjoHEFIL28ZSyo"));
			
			final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(creds)
					.withRegion("us-east-1")
					.build();
			return client;
			
		}
		else
		{
			return AmazonDynamoDBClientBuilder.defaultClient();
		}
	}


 


}

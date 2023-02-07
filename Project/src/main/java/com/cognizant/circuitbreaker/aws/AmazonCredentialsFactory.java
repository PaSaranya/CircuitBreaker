package com.cognizant.circuitbreaker.aws;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class AmazonCredentialsFactory {
	
	private static final String AWS_CREDENTIALS_FILE_PATH = "";
	private static final String AWS_PROFILE = "";
	private static final String PROFILE_NOT_FOUND = "";

	public ProfileCredentialsProvider getAmazonCredentials() {
		
		if(AWS_CREDENTIALS_FILE_PATH != null)
		{
			final String aws_credentials_file_path = AWS_CREDENTIALS_FILE_PATH;
			final String aws_profile = AWS_PROFILE;
			
			return new ProfileCredentialsProvider(aws_credentials_file_path, aws_profile);
		}
		else
		{
			throw new IllegalArgumentException(PROFILE_NOT_FOUND);
		}
		
	}


}

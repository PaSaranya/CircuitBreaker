package com.cognizant.circuitbreaker.custom;

import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cognizant.circuitbreaker.exception.HttpException;
import com.cognizant.circuitbreaker.model.CircuitBreakerConfigModel;
import com.cognizant.circuitbreaker.repository.CircuitBreakerConfigRepository;

//import com.cognizant.ratelimiter.exception.HttpException;

public class CircuitBreakerMain {

	public static void restTemplate()
	{
		  HttpHeaders headers = new HttpHeaders();
		  String url = "https://javatpoint.com";
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      try {
//			ResponseEntity r=CircuitBreakerUtil.doRestTemplatePost("https://javatpoint.com", entity, "String");
	    	  URL urlObj = new URL(url);
		      HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		      connection.setRequestMethod("GET");
		      connection.setUseCaches(false);
			CircuitBreakerUtil.doRequest(connection);
//			System.out.println(r.getStatusCodeValue());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		restTemplate();
//		CircuitBreakerConfigModel configModel = CircuitBreakerConfigRepository.getCircuitBreakerConfig("javatpoint.com");
//		System.out.println("****Config Model*****"+configModel.toString());
		
//		List<CircuitBreakerConfigModel> ct = CircuitBreakerConfigRepository.getCircuitBreakerVersions();
//		
//		System.out.println("****LIST Config Model*****"+ct.toString());
		
	}

}

package com.cognizant.circuitbreaker.custom;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.sql.SQLException;

import org.apache.http.concurrent.FutureCallback;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cognizant.circuitbreaker.exception.HttpException;
import com.cognizant.circuitbreaker.exception.ResilienceRsfHttpException;
import com.cognizant.circuitbreaker.exception.ResilientObjectException;
import com.cognizant.circuitbreaker.exception.ResilientUrlConnectionException;
import com.cognizant.circuitbreaker.repository.CircuitBreakerConfigRepository;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.SupplierUtils;
import oracle.jdbc.OracleCallableStatement;

public class CircuitBreakerUtil {
	
	public static HttpResponse doRequest(final HttpRequest httpRequest) throws HttpException
	{
		CircuitBreaker circuitBreaker;
		HttpResponse httpResponse;
		
		try {
			circuitBreaker = CircuitBreakerRegistryCustom.getCircuitBreaker(getRsfRequestHost(httpRequest));
			final Supplier<HttpResponse> supplier = () -> {
				return httpRequest.execute();
			};
			
			final Supplier<HttpResponse> supplierWithResultHandling = SupplierUtils.andThen(supplier, result ->{
				if(result.getResponseCode() >499 || (result.getResponseCode() == 408) || result.getResponseCode() == 425)
				{
					throw new ResilienceRsfHttpException(result);
				}
				return result; 
			});
			
			
			httpResponse = getRsfHttpResponse(circuitBreaker, supplierWithResultHandling);
			
			
		}
		catch(ResilienceRsfHttpException e)
		{
			if(e.getHttpResponse()!= null)
			{
				httpResponse = e.getHttpResponse();
			}
			else
			{
				throw (HttpException) e.getCause();
			}
		}
		catch(CallNotPermittedException e)
		{
			throw e;
		}
		catch(URISyntaxException e)
		{
			throw new HttpException(e.getMessage(), e);
		}
		
		return httpResponse;
		
	}
	
	public static HttpResponse doRequest(final HttpRequest httpRequest, final String circuitBreakerName) throws HttpException
	{
		return doRequest(httpRequest);
	}
	
	
	public static void doRequest(final HttpURLConnection httpUrlConnection) throws ResilientUrlConnectionException
	{
		final CircuitBreaker circuitBreaker = CircuitBreakerRegistryCustom.getCircuitBreaker(httpUrlConnection.getURL().getHost());
		System.out.println("*****CircuitBreakerUtil*****hostName***"+httpUrlConnection.getURL().getHost());
		System.out.println("*****CircuitBreakerUtil*****doRequest***");
		try {
			final Supplier<Void> supplier = () ->{
				try {
					httpUrlConnection.connect();
					System.out.println("*****httpUrlConnection.getResponseCode()****"+httpUrlConnection.getResponseCode());
					if(httpUrlConnection.getResponseCode()>499 || httpUrlConnection.getResponseCode()==408 || httpUrlConnection.getResponseCode()==425 || httpUrlConnection.getResponseCode()==429)
					{
						throw new ResilientUrlConnectionException();
					}
				}
				catch(IOException e)
				{
					throw new ResilientUrlConnectionException(e);
				}
				return null;
				
			};
			
			getHttpResponse(circuitBreaker, supplier);
		}
		catch(CallNotPermittedException e)
		{
			throw e;
		}
	}
	
	public static void doRequest(final HttpURLConnection httpUrlConnection, final String circuitBreakerName) throws IOException
	{
		doRequest(httpUrlConnection);
	}
	
	
	public static ResponseEntity<?> doRestTemplatePost(final String url, final HttpEntity<?> entity, final Object responseObj) throws HttpException, URISyntaxException
	{
		ResponseEntity<?> responseEntity;
		URI uri=new URI(url);
		String hostName = uri.getHost();
		
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("*****CircuitBreakerUtil*****hostName***"+hostName);
		System.out.println("*****CircuitBreakerUtil*****doRestTemplatePost***");
		final CircuitBreaker circuitBreaker = CircuitBreakerRegistryCustom.getCircuitBreaker(hostName);
		System.out.println("*****circuitBreaker values***"+circuitBreaker.getCircuitBreakerConfig());
		
		try {
			final Supplier<ResponseEntity<?>> supplier = () ->{
				
				return restTemplate.exchange(uri, HttpMethod.POST, entity, responseObj.getClass());
				
			};
			final Supplier<ResponseEntity<?>> supplierWithResultHandling = SupplierUtils.andThen(supplier, result -> {
				if(result.getStatusCodeValue() > 499 || result.getStatusCodeValue() == 408 || result.getStatusCodeValue() == 425 || result.getStatusCodeValue() ==429)
				{
					throw new ResilientObjectException(result);
				}
				return result;
			});
			
			responseEntity = getResponseEntity(circuitBreaker, supplier);
			
		}
		catch(ResilientObjectException e) {
			
			if(e.getResultObject() != null)
			{
				responseEntity = (ResponseEntity<?>)e.getResultObject();
			}
			else
			{
				throw (HttpException) e.getCause();
			}
		}
		catch(CallNotPermittedException e)
		{
			throw e;
		}
		return responseEntity;
	}
	
	
	public static void doRequest(final OracleCallableStatement callableStatement, final String breakerName) throws ResilientObjectException{
		final CircuitBreaker circuitBreaker = CircuitBreakerRegistryCustom.getCircuitBreaker(breakerName);
		
		try {
			final Supplier<Void> supplier = () -> {
				try {
					callableStatement.execute();
				}catch(NullPointerException | SQLException e)
				{
					throw new ResilientObjectException(e);
				}
				return null;
			};
			circuitBreaker.executeSupplier(supplier);
		}catch(ResilientObjectException e)
		{
			throw e;
		}
		catch(CallNotPermittedException e) {
			throw e;
		}
		catch(Exception e) {}
	}
	
	public static void setCircuitBreakerKey(final String envName, final String assetName, final String assetAreaName)
	{
		CircuitBreakerConfigRepository.setCircuitBreakerConfigKey(envName, assetName, assetAreaName);
	}
	
	private static ResponseEntity<?> getResponseEntity(CircuitBreaker circuitBreaker,
            Supplier<ResponseEntity<?>> supplier) {
        return circuitBreaker.executeSupplier(supplier);
    }

	static void getHttpResponse(final CircuitBreaker circuitBreaker, final Supplier<Void> supplier) {
        circuitBreaker.executeSupplier(supplier);
    }
	
	

	static HttpResponse getRsfHttpResponse(final CircuitBreaker circuitBreaker, final Supplier<HttpResponse> supplier) {
        return circuitBreaker.executeSupplier(supplier);
    }
	
	
	
    static String getRsfRequestHost(HttpRequest httpRequest) throws URISyntaxException {
		// TODO Auto-generated method stub
		return new URI(httpRequest.getUri()).getHost();
	}
    
    public static void doExecuteAsync(HttpRequest request, FutureCallback<HttpResponse> response)
    {
    	try {
    		CircuitBreaker circuitBreaker = CircuitBreakerRegistryCustom.getCircuitBreaker(getRsfRequestHost(request));
    		circuitBreaker.executeCompletionStage(() ->
    		{
    			final CompletableFuture<HttpResponse> fut = new CompletableFuture();
    			request.executeAsync( new FutureCallback<HttpResponse>()
						{
					public void completed(HttpResponse result)
					{
						if(result.getResponseCode() >499 || (result.getResponseCode() == 408) || result.getResponseCode() == 425 || result.getResponseCode() ==429)
						{
							fut.completeExceptionally(new ResilienceRsfHttpException(result));
						}
						else
						{
							fut.complete(result);
						}
						response.completed(result);
					}

				    @Override
				    public void cancelled() {
				    // TODO Auto-generated method stub
				    	fut.cancel(false);
				    	response.cancelled();

				    }

				    @Override
				    public void failed(Exception ex) {
				    // TODO Auto-generated method stub
				    	fut.completeExceptionally(ex);
				    	response.failed(ex);

				    } 
					
						});
    			return fut;
    		});
    	}
    	catch(URISyntaxException e)
    	{
    		response.failed(new HttpException(e.getMessage(),e));
    	}

    }



}

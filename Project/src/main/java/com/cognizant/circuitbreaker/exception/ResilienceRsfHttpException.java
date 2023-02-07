package com.cognizant.circuitbreaker.exception;

import com.cognizant.circuitbreaker.custom.HttpResponse;

public class ResilienceRsfHttpException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private HttpResponse httpResponse;
	
	public ResilienceRsfHttpException(HttpResponse httpResponse) {
		super();
		this.httpResponse = httpResponse;
	}

	public ResilienceRsfHttpException(Exception cause) {
		super(cause);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	
	

}

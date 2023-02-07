package com.cognizant.circuitbreaker.exception;

import org.springframework.http.ResponseEntity;

public class ResilientObjectException extends RuntimeException {
	
	private Object resultObject;

	public ResilientObjectException(final Object resultObject) {
		// TODO Auto-generated constructor stub
		super();
		this.resultObject=resultObject;
		
	}
	

	public ResilientObjectException(final Exception cause) {
		// TODO Auto-generated constructor stub
		super(cause);
		
	}

	public Object getResultObject() {
		// TODO Auto-generated method stub
		return resultObject;
	}

}

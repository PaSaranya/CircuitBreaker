package com.cognizant.circuitbreaker.exception;

import java.io.IOException;

public class ResilientUrlConnectionException extends RuntimeException {

	public ResilientUrlConnectionException() {
		// TODO Auto-generated constructor stub
	}
	public ResilientUrlConnectionException(final IOException e) {
		// TODO Auto-generated constructor stub
		super(e);
	}



}

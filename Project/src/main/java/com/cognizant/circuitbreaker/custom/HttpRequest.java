package com.cognizant.circuitbreaker.custom;

import org.apache.http.concurrent.FutureCallback;

public interface HttpRequest {

	HttpResponse execute();

	String getUri();

	void executeAsync(FutureCallback<HttpResponse> futureCallback);

}

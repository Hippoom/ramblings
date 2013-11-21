package com.github.hippoom.ramblings.mongogateway;

public class RestResponse<T> {
	private String statusCode;
	private String message;
	private T payload;

	public RestResponse(T payload) {
		this.statusCode = "STATUS_CODE_SUCCESS";
		this.message = "";
		this.payload = payload;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
}

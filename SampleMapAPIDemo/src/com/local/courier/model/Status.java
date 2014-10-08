package com.local.courier.model;

public class Status {

	private String status;
	private String message;
	private String sessionId;
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}

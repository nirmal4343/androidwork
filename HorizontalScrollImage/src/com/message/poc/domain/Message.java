package com.message.poc.domain;

public class Message {
	
	private String messageId;
	private String senderId;
	private boolean readStatus;
	private String subject;
	private String message;
	
	
	public Message(String messageId, String senderId, boolean readStatus,
			String subject, String message) {
		super();
		this.messageId = messageId;
		this.senderId = senderId;
		this.readStatus = readStatus;
		this.subject = subject;
		this.message = message;
	}
	
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public boolean getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}

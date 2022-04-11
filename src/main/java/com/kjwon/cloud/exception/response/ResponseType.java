package com.kjwon.cloud.exception.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ResponseType {
	
	protected String status;
	protected int statusCode;
	protected String message;
	protected LocalDateTime resTime;
	
	public ResponseType() {
		this.status = "None";
		this.statusCode = 0;
		this.message = "None";
		this.resTime = LocalDateTime.now();
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

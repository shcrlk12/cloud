package com.kjwon.cloud.exception.response;

import lombok.Getter;

@Getter
public class Success <T> extends ResponseType{
	private T data;
	
	public Success(T data) {
		super();
		
		this.data = data;
		this.status = "success";
	}
}

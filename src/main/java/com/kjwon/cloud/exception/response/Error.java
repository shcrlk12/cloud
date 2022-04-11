package com.kjwon.cloud.exception.response;

import lombok.Getter;

@Getter
public class Error<T> extends ResponseType{
	private T data;
	
	public Error(T data) {
		super();
		
		this.data = data;
		this.status = "error";
	}
}

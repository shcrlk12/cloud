package com.kjwon.cloud.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception{
	private int status;
	
	public CustomException(int status, String msg){
		super(msg);

		this.status = status;
	}

}

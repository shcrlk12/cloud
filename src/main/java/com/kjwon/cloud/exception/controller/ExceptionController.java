package com.kjwon.cloud.exception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.kjwon.cloud.exception.CustomException;
import com.kjwon.cloud.exception.response.Error;

@ControllerAdvice
@RestController
class ExceptionController{

	@ExceptionHandler(CustomException.class) 
	public Error<String> ex(CustomException e1) { 
		
		Error<String> e = new Error<String>("TEST");
		e.setMessage("TEST");
		e.setStatusCode(400);
		
		return e; 
	}

}
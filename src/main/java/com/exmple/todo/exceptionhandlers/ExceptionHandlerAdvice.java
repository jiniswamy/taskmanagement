package com.exmple.todo.exceptionhandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerAdvice {

	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String,String> handleTaskException(NotFoundException notFoundException) {
		Map<String,String> responseError = new HashMap();
		responseError.put("erroCode", "404");
		responseError.put("errorReason", notFoundException.getMessage());
		return responseError;
	}
}

package com.project.tracker.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.project.tracker.exception.CategoryException;
import com.project.tracker.exception.ExpenseException;
import com.project.tracker.exception.InvalidDateException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	Environment environment;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception, WebRequest webRequest) {
		ErrorInfo errorInfo = new ErrorInfo();
		
		errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setErrorMsg(environment.getProperty("General.EXCEPTION_MESSAGE"));
		errorInfo.setTimestamp(LocalDateTime.now());
		errorInfo.setWebRequestDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({CategoryException.class, ExpenseException.class})
	public ResponseEntity<ErrorInfo> categoryExceptionHandler(Exception exception, WebRequest webRequest) {
		ErrorInfo errorInfo = new ErrorInfo();
		
		if (exception instanceof CategoryException) {
			errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
			errorInfo.setErrorMsg(environment.getProperty(exception.getMessage()));
			errorInfo.setTimestamp(LocalDateTime.now());
			errorInfo.setWebRequestDetails(webRequest.getDescription(false));
		}
		else {
			errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
			errorInfo.setErrorMsg(environment.getProperty(exception.getMessage()));
			errorInfo.setTimestamp(LocalDateTime.now());
			errorInfo.setWebRequestDetails(webRequest.getDescription(false));
		}
		
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ErrorInfo> invalidDateExceptionHandler(InvalidDateException exception, WebRequest webRequest) {
		ErrorInfo errorInfo = new ErrorInfo();
		
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMsg(environment.getProperty(exception.getMessage()));
		errorInfo.setTimestamp(LocalDateTime.now());
		errorInfo.setWebRequestDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorInfo> validationExceptionHandler(Exception exception, WebRequest webRequest) {
		ErrorInfo errorInfo = new ErrorInfo();
		String msg;
		
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
			msg = manvException
					.getBindingResult()
					.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		} else {
			ConstraintViolationException cvException = (ConstraintViolationException) exception;
			msg = cvException
					.getConstraintViolations()
					.stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(", "));
		}
		
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMsg(msg);
		errorInfo.setTimestamp(LocalDateTime.now());
		errorInfo.setWebRequestDetails(webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
}

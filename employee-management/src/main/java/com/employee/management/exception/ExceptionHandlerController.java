package com.employee.management.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleResourceNotFound(final RecordNotFoundException exception,
			final HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		return error;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleException(final Exception exception,
			final HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		return error;
	}

}

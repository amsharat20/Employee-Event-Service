package com.employee.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception
{
	public RecordNotFoundException() {
		super();
	}
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}
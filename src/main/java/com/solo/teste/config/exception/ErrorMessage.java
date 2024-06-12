package com.solo.teste.config.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

	private int status;
	private String message;
	private String details;

	public ErrorMessage(HttpStatus status, String message, String details) {
		this.status = status.value();
		this.message = message;
		this.details = details;
	}
}

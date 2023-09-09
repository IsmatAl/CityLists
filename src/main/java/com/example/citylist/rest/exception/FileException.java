package com.example.citylist.rest.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

	private String customMessage;
	private String key;

	public FileException(String customMessage, String key) {
		this.customMessage = customMessage;
		this.key = key;
	}

}

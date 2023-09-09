package com.example.citylist.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class BusinessException extends RuntimeException {

	private String key;
	private List<String> values;

	public BusinessException(final String message, final String key, final List<String> values) {
		super(message);
		this.key = key;
		this.values = values;
	}
}

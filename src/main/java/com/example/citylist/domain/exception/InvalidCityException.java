package com.example.citylist.domain.exception;

import java.util.List;

public class InvalidCityException extends BusinessException {

	public InvalidCityException(final String message, final String key, final List<String> values) {
		super(message, key, values);
	}
}
package com.example.citylist.rest.exception;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorResource {

	String message;
	String code;
	List<String> values;
}

package com.example.citylist.rest.exception;

import com.example.citylist.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResource> methodArgumentNotValidException(
			final MethodArgumentNotValidException constraintViolationException) {
		log.error(constraintViolationException.getMessage());
		final StringBuilder error = new StringBuilder();
		constraintViolationException.getAllErrors()
				.forEach(e -> error.append(e.getDefaultMessage()).append(System.lineSeparator()));
		return ResponseEntity.badRequest().body(ErrorResource.builder().message(error.toString()).build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<ErrorResource> handleBusinessException(final BusinessException businessException) {
		log.error(businessException.getMessage());
		return ResponseEntity.badRequest().body(ErrorResource.builder().message(businessException.getMessage())
				.code(businessException.getKey()).values(businessException.getValues()).build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResource> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException httpMessageNotReadableException) {
		return ResponseEntity.badRequest().body(ErrorResource.builder()
				.message(httpMessageNotReadableException.getMessage()).code("invalid-field").build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileException.class)
	public ResponseEntity<ErrorResource> handleFileException(final FileException fileException) {
		return ResponseEntity.badRequest().body(
				ErrorResource.builder().message(fileException.getCustomMessage()).code(fileException.getKey()).build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileSizeLimitExceededException.class)
	public ResponseEntity<ErrorResource> handleFileSizeLimitExceededException(
			final FileSizeLimitExceededException fileSizeExceededException) {
		return ResponseEntity.badRequest().body(ErrorResource.builder().message(fileSizeExceededException.getMessage())
				.code("file-size-limit-exceeded").build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SizeLimitExceededException.class)
	public ResponseEntity<ErrorResource> handleSizeLimitExceededException(
			final SizeLimitExceededException sizeLimitExceededException) {
		return ResponseEntity.badRequest().body(ErrorResource.builder().message(sizeLimitExceededException.getMessage())
				.code("request-size-limit-exceeded").build());
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ErrorResource> handleOptimisticLockingFailureException() {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResource.builder()
				.message("Data was updated by another user").code("data-updated-by-another-user").build());
	}
}

package com.mei.serviceprovider.common.validation;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandleValidationError extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<String> errors = ex.getBindingResult().getFieldErrors() //
                .stream().map(FieldError::getDefaultMessage) //
                .map(error -> messageSource.getMessage(error, null, Locale.getDefault())).collect(Collectors.toList());

        final ApiError apiError = ApiError.builder() //
                .message(ex.getMessage()) //
                .status(HttpStatus.BAD_REQUEST) //
                .errors(errors) //
                .methodName(ex.getParameter().getMethod().getName()) //
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> constraintViolateException(ConstraintViolationException ex, WebRequest request) {

        final ApiError apiError = ApiError.builder() //
                .message(ex.getMessage()) //
                .errors(List.of(messageSource.getMessage(ex.getConstraintName(), null, Locale.getDefault()))) //
                .status(HttpStatus.CONFLICT) //
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

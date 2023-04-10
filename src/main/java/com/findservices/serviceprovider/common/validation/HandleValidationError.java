package com.findservices.serviceprovider.common.validation;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
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

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ControllerAdvice
public class HandleValidationError extends ResponseEntityExceptionHandler {

    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final Set<String> errors = ex.getBindingResult().getFieldErrors() //
                .stream() //
                .map(this::formatErrorMessage) //
                .collect(Collectors.toSet());

        final ApiError apiError = ApiError.builder() //
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
                .errors(Set.of(messageSource.getMessage(ex.getConstraintName(), null, Locale.getDefault()))) //
                .status(HttpStatus.CONFLICT) //
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HandleException.class)
    public final ResponseEntity<ApiError> handleCustomExceptions(HandleException ex, WebRequest request) {
        ApiError error = ApiError.builder()
                .status(ex.getHttpStatus())
                .errors(Set.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String formatErrorMessage(FieldError error) {
        return switch (error.getCode()) {
            case "NotBlank", "NotNull", "NotEmpty" -> messageSource.getMessage( //
                    TranslationConstants.VALIDATION_GENERIC_REQUIRED_FIELD,  //
                    new Object[]{error.getField()},  //
                    Locale.getDefault() //
            );
            case "Size" -> messageSource.getMessage( //
                    TranslationConstants.VALIDATION_GENERIC_MAX_SIZE_FIELD,  //
                    new Object[]{error.getField(), error.getArguments()[1]},  //
                    Locale.getDefault() //
            );
            case "Min" -> messageSource.getMessage( //
                    TranslationConstants.VALIDATION_GENERIC_MIN_FIELD,  //
                    new Object[]{error.getField(), error.getArguments()[1]},  //
                    Locale.getDefault() //
            );
            default -> error.getDefaultMessage();
        };
    }
}

package com.findservices.serviceprovider.common.validation;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.login.exceptions.InvalidJwtAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ControllerAdvice
public class HandleValidationError extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint {

    @Autowired
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

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> handleAuthenticationException(Exception ex) {

        final ApiError error = ApiError.builder() //
                .status(HttpStatus.UNAUTHORIZED) //
                .message("Authentication failed at controller advice") //
                .build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler({ConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> constraintViolateException(ConstraintViolationException ex, WebRequest request) {

        final ApiError apiError = ApiError.builder() //
                .message(ex.getMessage()) //
                .errors(Set.of(messageSource.getMessage(ex.getConstraintName(), null, Locale.getDefault()))) //
                .status(ex.getConstraintName().contains("fk") ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT) //
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

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ApiError> accessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
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

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ApiError> handleInvalidJwtAuthenticationExceptions(
            Exception ex, WebRequest request) {

        ApiError exceptionResponse = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .methodName(request.getContextPath())
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    private String formatErrorMessage(FieldError error) {
        return switch (error.getCode()) {
            case "NotBlank", "NotNull", "NotEmpty" -> messageSource.getMessage( //
                    TranslationConstants.VALIDATION_GENERIC_REQUIRED_FIELD,  //
                    new Object[]{error.getField()},  //
                    Locale.getDefault() //
            );
            case "Size" -> {
                if (Objects.equals(error.getDefaultMessage(), "max")) {
                    yield messageSource.getMessage( //
                            TranslationConstants.VALIDATION_GENERIC_MAX_SIZE_FIELD,  //
                            new Object[]{error.getField(), error.getArguments()[2]},  //
                            Locale.getDefault() //
                    );
                } else {
                    yield messageSource.getMessage( //
                            TranslationConstants.VALIDATION_GENERIC_MIN_SIZE_FIELD,  //
                            new Object[]{error.getField(), error.getArguments()[2]},  //
                            Locale.getDefault() //
                    );
                }
            }
            case "Min" -> messageSource.getMessage( //
                    TranslationConstants.VALIDATION_GENERIC_MIN_FIELD,  //
                    new Object[]{error.getField(), error.getArguments()[1]},  //
                    Locale.getDefault() //
            );
            default -> error.getDefaultMessage();
        };
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
    }
}

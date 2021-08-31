package com.chandrakumar.ms.api.error;

import com.chandrakumar.ms.api.error.model.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException methodArgumentNotValidException) {

        final BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        final List<ObjectError> allErrors = bindingResult.getAllErrors();

        if (allErrors.size() > 1) {


            final List<ApiError> apiError = allErrors.stream()
                    .map(objectError -> getMessage(Objects.requireNonNull(objectError.getCodes())[0]))
                    .map(message -> ApiError.builder()
                            .type(BAD_REQUEST.getReasonPhrase())
                            .code(BAD_REQUEST.value())
                            .message(getMessage(message))
                            .timestamp(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());


            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(getMessage(Objects.requireNonNull(allErrors.get(0).getCodes())[0]))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public final ResponseEntity<ApiError> handleHttpMediaTypeNotAcceptableException(
            final HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException,
            final WebRequest webRequest) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(getMessage(httpMediaTypeNotAcceptableException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(HttpHeaderException.class)
    public final ResponseEntity<ApiError> handleHttpHeaderException(
            final HttpHeaderException httpHeaderException) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(getMessage(httpHeaderException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(FieldValidationException.class)
    public final ResponseEntity<ApiError> handleFieldValidationException(
            final FieldValidationException fieldValidationException) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(getMessage(fieldValidationException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public final ResponseEntity<ApiError> handleNoRecordFoundException(
            final NoRecordFoundException noRecordFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(OK.getReasonPhrase())
                .code(OK.value())
                .message(getMessage(noRecordFoundException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiError> handleResourceNotFoundException(
            final ResourceNotFoundException resourceNotFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(NOT_FOUND.getReasonPhrase())
                .code(NOT_FOUND.value())
                .message(getMessage(resourceNotFoundException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyFoundException.class)
    public final ResponseEntity<ApiError> handleResourceAlreadyFoundException(
            final ResourceAlreadyFoundException resourceAlreadyFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(CONFLICT.getReasonPhrase())
                .code(CONFLICT.value())
                .message(getMessage(resourceAlreadyFoundException.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, CONFLICT);
    }

    private String getMessage(final String message) {

        final Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(message, null, locale);
        } catch (NoSuchMessageException e) {
            return message;
        }
    }
}

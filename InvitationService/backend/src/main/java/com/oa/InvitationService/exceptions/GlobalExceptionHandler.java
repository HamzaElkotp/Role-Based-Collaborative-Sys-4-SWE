package com.oa.InvitationService.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodNotValidArgument(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errorMap.put(name, message);
        });
        return errorMap;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // This turns the 500 into a 404
    public Map<String, String> handleEntityNotFound(EntityNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", e.getMessage());
        return errorMap;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Use 400
    public Map<String, String> handleIllegalState(IllegalStateException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", e.getMessage());
        return errorMap;
}
}
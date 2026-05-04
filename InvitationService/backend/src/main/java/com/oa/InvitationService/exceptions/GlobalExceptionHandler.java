package com.oa.InvitationService.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
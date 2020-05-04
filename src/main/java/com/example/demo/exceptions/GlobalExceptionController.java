package com.example.demo.exceptions;


import com.example.demo.pojo.exceptions.AuthenticationException;
import com.example.demo.pojo.exceptions.BadRequestException;
import com.example.demo.pojo.exceptions.ExceptionResponse;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;


@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {


    private static final Logger logger = LogManager.getLogger();


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(WebRequest request, Exception ex) {
        ExceptionResponse e = new ExceptionResponse();
        logger.error(ex.getMessage(), ex);
        HttpStatus status = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        e.setCode(status.value());
        e.setMessage(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, e, headers, status, request);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBadRequestException(WebRequest request, BadRequestException ex) {
        ExceptionResponse e = new ExceptionResponse();
        int badRequestErrorCode = ex.getErrorCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();
        e.setCode(badRequestErrorCode);
        e.setMessage(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, e, headers, status, request);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAuthException(WebRequest request, AuthenticationException ex) {
        ExceptionResponse e = new ExceptionResponse();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();
        e.setCode(status.value());
        e.setMessage(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, e, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse e = new ExceptionResponse();
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        String message = "Validation Errors: " +details.toString();
        e.setCode(status.value());
        e.setMessage(message);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, e, headers, status, request);
    }

}

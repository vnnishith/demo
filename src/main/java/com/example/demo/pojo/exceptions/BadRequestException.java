package com.example.demo.pojo.exceptions;

public class BadRequestException extends RuntimeException {
    private int errorCode;

    public BadRequestException() {
    }

    public BadRequestException(String message, int code) {
        super(message);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

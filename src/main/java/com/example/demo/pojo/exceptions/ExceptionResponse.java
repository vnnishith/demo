package com.example.demo.pojo.exceptions;

import com.example.demo.pojo.AbstractResponse;

public class ExceptionResponse extends AbstractResponse {

    private String error;

    public ExceptionResponse() {

    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object data) {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}


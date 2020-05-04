package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response extends AbstractResponse {

    Object data;
    public Response(Object data) {
        setData(data);
    }
    public Response(Object data,String message) {
        setData(data);
        setMessage(message);
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data=data;
    }
}

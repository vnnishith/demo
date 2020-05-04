package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public abstract class AbstractResponse {

    /**
     * The Code.
     */
    Integer code;
    /**
     * The Message.
     */
    String message = "success";

    /**
     * Gets code.
     *
     * @return the code
     */
    @JsonGetter("code")
    public Integer getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    @JsonGetter("data")
    public abstract Object getData();

    /**
     * Sets data.
     *
     * @param data the data
     */
    public abstract void setData(Object data);
}
package com.fsociety.authapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBody<T> {

    private boolean success;
    private T data;
    private String message;

    public ResponseBody() {
        this.success = true;
    }

    public ResponseBody(T data) {
        this.success = true;
        this.data = data;
    }

    public ResponseBody(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
    }

    //false
    public ResponseBody(boolean success) {
        this.success = success;
    }

    public ResponseBody(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResponseBody(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}

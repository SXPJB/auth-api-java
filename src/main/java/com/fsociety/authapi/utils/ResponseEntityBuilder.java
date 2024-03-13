package com.fsociety.authapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder<T> {

  private HttpStatus status;
  private boolean success;
  private String msg;
  private T data;

  public ResponseEntityBuilder<T> withStatus(HttpStatus status) {
    this.status = status;
    return this;
  }

  public ResponseEntityBuilder<T> withSuccess(boolean success) {
    this.success = success;
    return this;
  }

  public ResponseEntityBuilder<T> withMessage(String msg) {
    this.msg = msg;
    return this;
  }

  public ResponseEntityBuilder<T> withData(T data) {
    this.data = data;
    return this;
  }

  public ResponseEntity<ResponseBody<T>> build() {
    ResponseBody<T> body = new ResponseBody<>(success, data, msg);
    return new ResponseEntity<>(body, status);
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

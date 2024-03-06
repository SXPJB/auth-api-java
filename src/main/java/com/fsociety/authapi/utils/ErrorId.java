package com.fsociety.authapi.utils;

import lombok.Getter;

@Getter
public enum ErrorId {
  CONSTRAINT_VIOLATION("CONSTRAINT_VIOLATION"),
  NOT_FOUND("NOT_FOUND"),
  DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION"),
  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
  UNAUTHORIZED("UNAUTHORIZED"),
  FORBIDDEN("FORBIDDEN"),
  BAD_REQUEST("BAD_REQUEST"),
  ;

  private final String id;

  ErrorId(String id) {
    this.id = id;
  }
}

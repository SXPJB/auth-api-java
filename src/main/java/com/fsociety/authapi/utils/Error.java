package com.fsociety.authapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
  private String id;
  private String filed;
  private String message;
}

package com.fsociety.authapi.utils;

public class NotFoundException extends Exception {
  private String resourceName;

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String resourceName, String message) {
    super(message);
    this.resourceName = resourceName;
  }

  public String getResourceName() {
    return resourceName;
  }
}

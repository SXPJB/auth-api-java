package com.fsociety.authapi.app.register;

public interface ConfirmUserService {
  boolean confirmUser(String username, String confirmationCode);
}

package com.fsociety.authapi.app.register;

import com.fsociety.authapi.utils.NotFoundException;

public interface ConfirmUserService {
  boolean confirmUser(String username, String confirmationCode);

  void resendConfirmationEmail(String username) throws NotFoundException;
}

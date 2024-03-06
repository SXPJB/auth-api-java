package com.fsociety.authapi.app.emailprovider;

public interface EmailService {
  void sendConfirmationEmail(String username, String email, String confirmationCode);
}

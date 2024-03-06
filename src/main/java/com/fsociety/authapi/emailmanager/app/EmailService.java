package com.fsociety.authapi.emailmanager.app;

public interface EmailService {
  void sendConfirmationEmail(String username, String email, String confirmationCode);
}

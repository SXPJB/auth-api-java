package com.fsociety.authapi.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
  public static boolean isPasswordValid(String password, String storedPassword) {
    return BCrypt.checkpw(password, storedPassword);
  }

  public static String encryptPassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }
}

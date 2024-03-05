package com.fsociety.authapi.confirmuser.app;

public interface ConfirmUserService {
    boolean confirmUser(String username, String confirmationCode);
}

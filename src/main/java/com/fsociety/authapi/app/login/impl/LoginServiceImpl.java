package com.fsociety.authapi.app.login.impl;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import com.fsociety.authapi.http.login.dto.LoginResponseBuilder;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

  private final JwtUtils jwtUtils;
  private final UserRepository userRepository;

  public LoginServiceImpl(JwtUtils jwtUtils, UserRepository userRepository) {
    this.jwtUtils = jwtUtils;
    this.userRepository = userRepository;
  }

  @Override
  public LoginResponseDTO login(String username, String password)
      throws LoginAuthenticationException {

    User user =
        userRepository
            .findByUsernameAndActiveIsTrue(username)
            .orElseThrow(() -> new LoginAuthenticationException("User not found"));

    if (!isPasswordValid(password, user.getPassword())) {
      throw new LoginAuthenticationException("Invalid password");
    }

    if (!user.getIsConfirmed()) {
      throw new LoginAuthenticationException("User not confirmed");
    }
    String token = jwtUtils.generateToken(user.getUsername(), user.toMap());
    user.setToken(token);
    userRepository.save(user);
    return new LoginResponseBuilder()
        .fromUser(user)
        .withToken(user.getToken())
        .withTokenType()
        .withExpiresIn2hours()
        .build();
  }

  private boolean isPasswordValid(String password, String storedPassword) {
    return BCrypt.checkpw(password, storedPassword);
  }
}

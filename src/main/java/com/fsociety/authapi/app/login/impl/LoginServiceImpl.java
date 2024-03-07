package com.fsociety.authapi.app.login.impl;

import static com.fsociety.authapi.utils.PasswordUtils.isPasswordValid;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import com.fsociety.authapi.http.login.dto.LoginRequestDTO;
import com.fsociety.authapi.http.login.dto.LoginResponseBuilder;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

  private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

  private final JwtUtils jwtUtils;
  private final UserRepository userRepository;

  @Override
  public LoginResponseDTO login(LoginRequestDTO dto) throws LoginAuthenticationException {
    log.info("Logging in user: \n{}", dto.getUsername());

    User user =
        userRepository
            .findByUsernameAndActiveIsTrue(dto.getUsername())
            .orElseThrow(() -> new LoginAuthenticationException("User not found"));

    if (!isPasswordValid(dto.getPassword(), user.getPassword())) {
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
}

package com.fsociety.authapi.app.register.impl;

import com.fsociety.authapi.app.register.ConfirmUserService;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmUserServiceImpl implements ConfirmUserService {

  private final UserRepository userRepository;

  @Override
  public boolean confirmUser(String username, String confirmationCode) {
    if (username == null
        || username.isEmpty()
        || confirmationCode == null
        || confirmationCode.isEmpty()) {
      return false;
    }
    Optional<User> user =
        userRepository.findByUsernameAndConfirmationCodeAndActiveIsTrue(username, confirmationCode);
    if (user.isEmpty()) {
      return false;
    }
    Timestamp now = new Timestamp(LocalDateTime.now().toDate().getTime());
    if (user.get().getConfirmationCodeExpires().before(now)) {
      return false;
    }
    user.get().setIsConfirmed(true);
    user.get().setConfirmationCode(null);
    user.get().setConfirmationCodeExpires(null);
    userRepository.save(user.get());
    return true;
  }
}

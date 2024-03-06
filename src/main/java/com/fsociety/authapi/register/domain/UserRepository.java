package com.fsociety.authapi.register.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsernameAndConfirmationCodeAndActiveIsTrue(
      String username, String confirmationCode);
}

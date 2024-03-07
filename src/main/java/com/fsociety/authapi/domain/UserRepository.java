package com.fsociety.authapi.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsernameAndConfirmationCodeAndActiveIsTrue(
      String username, String confirmationCode);

  Optional<User> findByUsernameAndActiveIsTrue(String username);
}

package com.fsociety.authapi.unit.app.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fsociety.authapi.app.emailprovider.EmailService;
import com.fsociety.authapi.app.register.impl.ConfirmUserServiceImpl;
import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import com.fsociety.authapi.utils.NotFoundException;
import java.sql.Timestamp;
import java.util.Optional;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConfirmUserServiceImplTests {
  @Mock private UserRepository userRepository;
  @Mock private EmailService emailService;

  @InjectMocks private ConfirmUserServiceImpl confirmUserService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void confirmUser() {
    String username = "username";
    String confirmationCode = "confirmationCode";
    User user = new User();
    user.setUsername(username);
    user.setConfirmationCode(confirmationCode);
    user.setConfirmationCodeExpires(
        new Timestamp(LocalDateTime.now().plusDays(1).toDateTime().getMillis()));

    when(userRepository.findByUsernameAndConfirmationCodeAndActiveIsTrue(
            username, confirmationCode))
        .thenReturn(Optional.of(user));

    boolean result = confirmUserService.confirmUser(username, confirmationCode);

    assertTrue(result);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void confirmUserWithNullUsername() {
    String confirmationCode = "confirmationCode";
    boolean result = confirmUserService.confirmUser(null, confirmationCode);
    assertFalse(result);
  }

  @Test
  void resendConfirmationEmail() throws NotFoundException {
    var username = "username";
    var user = new User();
    user.setUsername(username);
    var person = new Person();
    person.setEmail("email@test.com");
    user.setPerson(person);
    user.setConfirmationCodeExpires(
        new Timestamp(LocalDateTime.now().minusDays(1).toDateTime().getMillis()));

    when(userRepository.findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username))
        .thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);
    confirmUserService.resendConfirmationEmail(username);

    verify(emailService, times(1))
        .sendConfirmationEmail(username, person.getEmail(), user.getConfirmationCode());
    verify(userRepository, times(1)).findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void resendConfirmationEmailWithNotFound() {
    var username = "username";
    when(userRepository.findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username))
        .thenReturn(Optional.empty());
    var exception =
        assertThrows(
            NotFoundException.class, () -> confirmUserService.resendConfirmationEmail(username));
    assertNotNull(exception);
    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username);
    verifyNoInteractions(emailService);
  }

  @Test
  void resendConfirmationEmailWithConfirmationCodeStillValid() {
    var username = "username";
    var user = new User();
    user.setUsername(username);
    var person = new Person();
    person.setEmail("email@test.com");
    user.setPerson(person);
    user.setConfirmationCodeExpires(
        new Timestamp(LocalDateTime.now().plusDays(1).toDateTime().getMillis()));

    when(userRepository.findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username))
        .thenReturn(Optional.of(user));

    var exception =
        assertThrows(
            NotFoundException.class, () -> confirmUserService.resendConfirmationEmail(username));
    assertNotNull(exception);
    assertEquals("Confirmation code still valid", exception.getMessage());
    verify(userRepository, times(1)).findByUsernameAndActiveIsTrueAndIsConfirmedIsFalse(username);
    verifyNoInteractions(emailService);
  }
}

package com.fsociety.authapi.unit.app.login;

import static com.fsociety.authapi.utils.PasswordUtils.encryptPassword;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fsociety.authapi.app.login.impl.JwtUtils;
import com.fsociety.authapi.app.login.impl.LoginServiceImpl;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import com.fsociety.authapi.http.login.dto.LoginRequestDTO;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginServiceImplTests {
  @Mock private JwtUtils jwtUtils;
  @Mock private UserRepository userRepository;
  @InjectMocks private LoginServiceImpl loginService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loginSuccess() throws LoginAuthenticationException {

    String username = "user";
    String password = "password";
    User user = buildUser(username, password);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setUsername(user.getUsername());
    loginRequestDTO.setPassword(password);

    when(userRepository.findByUsernameAndActiveIsTrue(user.getUsername()))
        .thenReturn(Optional.of(user));
    when(jwtUtils.generateToken(user.getUsername(), user.toMap())).thenReturn("token");

    LoginResponseDTO responseDTO = loginService.login(loginRequestDTO);
    assertNotNull(responseDTO);
    assertEquals(responseDTO.getToken(), "token");
    assertEquals(responseDTO.getTokenType(), "Bearer");
    assertEquals(responseDTO.getExpiresIn(), 72800);
  }

  @Test
  void loginFailUserNotFound() {
    String username = "user";
    String password = "password";
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setUsername(username);
    loginRequestDTO.setPassword(password);

    when(userRepository.findByUsernameAndActiveIsTrue(username)).thenReturn(Optional.empty());

    LoginAuthenticationException exception =
        Assertions.assertThrows(
            LoginAuthenticationException.class, () -> loginService.login(loginRequestDTO));

    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void loginFailUserNotConfirmed() {
    String username = "user";
    String password = "password";
    User user = buildUserNotConfirmed(username, password);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setUsername(username);
    loginRequestDTO.setPassword(password);

    when(userRepository.findByUsernameAndActiveIsTrue(username)).thenReturn(Optional.of(user));

    LoginAuthenticationException exception =
        assertThrows(LoginAuthenticationException.class, () -> loginService.login(loginRequestDTO));
    assertEquals("User not confirmed", exception.getMessage());
  }

  private User buildUser(String username, String pass) {
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername(username);
    user.setPassword(encryptPassword(pass));
    user.setIsConfirmed(true);
    user.setActive(true);
    Person person = new Person();
    person.setId(UUID.randomUUID());
    person.setFirstName("first");
    person.setLastName("last");
    person.setGender(new Catalog(UUID.randomUUID(), "c_gender", "F", "Female", true));
    user.setPerson(person);
    return user;
  }

  private User buildUserNotConfirmed(String username, String pass) {
    User user = buildUser(username, pass);
    user.setIsConfirmed(false);
    return user;
  }
}

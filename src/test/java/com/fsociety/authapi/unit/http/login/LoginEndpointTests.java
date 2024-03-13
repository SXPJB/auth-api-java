package com.fsociety.authapi.unit.http.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.http.login.LoginEndpoint;
import com.fsociety.authapi.http.login.dto.LoginRequestDTO;
import com.fsociety.authapi.http.login.dto.LoginResponseBuilder;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import com.fsociety.authapi.utils.ResponseBody;
import java.sql.Timestamp;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class LoginEndpointTests {

  @Mock private LoginService loginService;

  @InjectMocks private LoginEndpoint loginEndpoint;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loginSuccess() throws LoginAuthenticationException {
    LoginRequestDTO loginRequestDTO = buildSuccessRequestDTO();
    LoginResponseDTO loginResponseDTO = buildResponseDTO();

    when(loginService.login(loginRequestDTO)).thenReturn(loginResponseDTO);
    ResponseEntity<ResponseBody<LoginResponseDTO>> response = loginEndpoint.login(loginRequestDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(loginResponseDTO, response.getBody().getData());
    assertTrue(response.getBody().isSuccess());
  }

  @Test
  void loginFailure() throws LoginAuthenticationException {
    LoginRequestDTO loginRequestDTO = buildFailureRequestDTO();
    when(loginService.login(loginRequestDTO))
        .thenThrow(new LoginAuthenticationException("Error logging in"));
    LoginAuthenticationException exception =
        assertThrows(
            LoginAuthenticationException.class, () -> loginEndpoint.login(loginRequestDTO));
    assertEquals("Error logging in", exception.getMessage());
  }

  @Test
  void userNotFound() throws LoginAuthenticationException {
    LoginRequestDTO loginRequestDTO = buildFailureRequestDTO();
    loginRequestDTO.setUsername("user_not_found");
    loginRequestDTO.setPassword("user_not_found");
    when(loginService.login(loginRequestDTO))
        .thenThrow(new LoginAuthenticationException("User not found"));
    LoginAuthenticationException exception =
        assertThrows(
            LoginAuthenticationException.class, () -> loginEndpoint.login(loginRequestDTO));
    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void invalidPassword() throws LoginAuthenticationException {
    LoginRequestDTO loginRequestDTO = buildFailureRequestDTO();
    loginRequestDTO.setUsername("test");
    loginRequestDTO.setPassword("invalid_password");
    when(loginService.login(loginRequestDTO))
        .thenThrow(new LoginAuthenticationException("Invalid password"));
    LoginAuthenticationException exception =
        assertThrows(
            LoginAuthenticationException.class, () -> loginEndpoint.login(loginRequestDTO));
    assertEquals("Invalid password", exception.getMessage());
  }

  @Test
  void userNotConfirmed() throws LoginAuthenticationException {
    LoginRequestDTO loginRequestDTO = buildFailureRequestDTO();
    loginRequestDTO.setUsername("test");
    loginRequestDTO.setPassword("test");
    when(loginService.login(loginRequestDTO))
        .thenThrow(new LoginAuthenticationException("User not confirmed"));
    LoginAuthenticationException exception =
        assertThrows(
            LoginAuthenticationException.class, () -> loginEndpoint.login(loginRequestDTO));
    assertEquals("User not confirmed", exception.getMessage());
  }

  private LoginRequestDTO buildSuccessRequestDTO() {
    return new LoginRequestDTO("test", "test");
  }

  private LoginRequestDTO buildFailureRequestDTO() {
    return new LoginRequestDTO();
  }

  private LoginResponseDTO buildResponseDTO() {
    return new LoginResponseBuilder()
        .fromUser(
            new User(
                UUID.randomUUID(),
                new Person(
                    UUID.randomUUID(),
                    "test",
                    "test",
                    "test",
                    new Catalog(UUID.randomUUID(), "c_gender", "M", "Male", true),
                    true,
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis())),
                "test",
                "test",
                "token",
                true,
                "confirmationCode",
                new Timestamp(System.currentTimeMillis()),
                true,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())))
        .withToken("token")
        .withExpiresIn2hours()
        .withTokenType()
        .build();
  }
}

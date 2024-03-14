package com.fsociety.authapi.unit.http.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fsociety.authapi.app.register.ConfirmUserService;
import com.fsociety.authapi.http.register.ConfirmationUserEndpoint;
import com.fsociety.authapi.utils.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class ConfirmationUserEndpointTests {

  @Mock private ConfirmUserService confirmUserService;

  @InjectMocks private ConfirmationUserEndpoint confirmationUserEndpoint;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void confirmUserSuccess() {
    var username = "testUser";
    var confirmationCode = "ashdakjsdnlqi";

    when(confirmUserService.confirmUser(username, confirmationCode)).thenReturn(true);
    var response = confirmationUserEndpoint.confirmUser(username, confirmationCode);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    String message = response.getBody().getMessage();
    assertTrue(message.contains("Thank You confirm your registration!"));
  }

  @Test
  void confirmUserFailure() {
    var username = "testUser";
    var confirmationCode = "wrongCode";
    when(confirmUserService.confirmUser(username, confirmationCode)).thenReturn(false);
    var response = confirmationUserEndpoint.confirmUser(username, confirmationCode);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    String message = response.getBody().getMessage();
    assertTrue(message.contains("Having an error..."));
  }

  @Test
  void resendConfirmationCodeSuccess() throws NotFoundException {
    var username = "testUsername";
    doNothing().when(confirmUserService).resendConfirmationEmail(username);
    var response = confirmationUserEndpoint.reconfirmUser(username);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isSuccess());
    String message = response.getBody().getMessage();
    assertTrue(message.contains("Confirmation email sent successfully!"));
  }

  @Test
  void resendConfirmationCodeUserNotFound() throws NotFoundException {
    var username = "userNotFound";
    var errorMgs = "User not found";
    doThrow(new NotFoundException(errorMgs))
        .when(confirmUserService)
        .resendConfirmationEmail(username);
    var response = confirmationUserEndpoint.reconfirmUser(username);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    String message = response.getBody().getMessage();
    assertTrue(message.contains(errorMgs));
  }

  @Test
  void resendConfirmationCodeConfirmationCodeStillValid() throws NotFoundException {
    var username = "userNotFound";
    var errorMgs = "Confirmation code still valid";
    doThrow(new NotFoundException(errorMgs))
        .when(confirmUserService)
        .resendConfirmationEmail(username);
    var response = confirmationUserEndpoint.reconfirmUser(username);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    String message = response.getBody().getMessage();
    assertTrue(message.contains(errorMgs));
  }
}

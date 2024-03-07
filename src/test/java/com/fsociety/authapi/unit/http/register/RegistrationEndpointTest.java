package com.fsociety.authapi.unit.http.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fsociety.authapi.app.register.RegisterService;
import com.fsociety.authapi.http.register.RegisterEndpoint;
import com.fsociety.authapi.http.register.dto.UserRequestDTO;
import com.fsociety.authapi.http.register.dto.UserResponseDTO;
import com.fsociety.authapi.utils.RegistrationException;
import com.fsociety.authapi.utils.ResponseBody;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegistrationEndpointTest {

  private static final Logger log = LoggerFactory.getLogger(RegistrationEndpointTest.class);

  @Mock private RegisterService registerService;

  @InjectMocks private RegisterEndpoint registerEndpoint;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void registerSuccess() throws Exception {
    UserRequestDTO userRequestDTO = buildRequestDTO();
    UserResponseDTO userResponseDTO = buildResponseDTO();

    when(registerService.register(userRequestDTO)).thenReturn(userResponseDTO);
    ResponseEntity<ResponseBody<UserResponseDTO>> response =
        registerEndpoint.register(userRequestDTO);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(userResponseDTO, response.getBody().getData());
    assertTrue(response.getBody().isSuccess());
  }

  @Test
  public void registerFailure() {

    when(registerService.register(null))
        .thenThrow(new RegistrationException("Error registering user", new Exception()));

    RegistrationException exception =
        assertThrows(RegistrationException.class, () -> registerEndpoint.register(null));
    assertEquals("Error registering user", exception.getMessage());
  }

  private UserRequestDTO buildRequestDTO() {
    UserRequestDTO userRequestDTO = new UserRequestDTO();
    userRequestDTO.setUsername("username");
    userRequestDTO.setPassword("password");
    userRequestDTO.setFirstName("firstName");
    userRequestDTO.setLastName("lastName");
    userRequestDTO.setEmail("email");
    userRequestDTO.setGender("M");
    return userRequestDTO;
  }

  public UserResponseDTO buildResponseDTO() {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setIdUser(UUID.randomUUID());
    userResponseDTO.setIdPerson(UUID.randomUUID());
    userResponseDTO.setUsername("username");
    userResponseDTO.setFirstName("firstName");
    userResponseDTO.setLastName("lastName");
    userResponseDTO.setGender("M");
    userResponseDTO.setEmail("email");
    return userResponseDTO;
  }
}

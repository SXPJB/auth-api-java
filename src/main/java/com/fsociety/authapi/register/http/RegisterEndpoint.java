package com.fsociety.authapi.register.http;

import com.fsociety.authapi.register.app.RegisterService;
import com.fsociety.authapi.register.domain.dto.UserRequestDTO;
import com.fsociety.authapi.register.domain.dto.UserResponseDTO;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Endpoint for user registration. */
@RestController
@RequestMapping("/v1/register")
public class RegisterEndpoint {

  private final RegisterService registerService;

  public RegisterEndpoint(RegisterService registerService) {
    this.registerService = registerService;
  }

  /**
   * Register a new user.
   *
   * @param userRegisterDTO the user registration details
   * @return the response entity with the registered user details
   * @throws Exception if an error occurs during registration this exception will catch by the
   *     global exception handler
   */
  @PostMapping
  public ResponseEntity<ResponseBody<UserResponseDTO>> register(
      @Valid @RequestBody UserRequestDTO userRegisterDTO) throws Exception {
    UserResponseDTO registeredUser = registerService.register(userRegisterDTO);
    return buildResponse(registeredUser);
  }

  /**
   * Build the response entity with the registered user details.
   *
   * @param registeredUser the registered user details
   * @return the response entity
   */
  private ResponseEntity<ResponseBody<UserResponseDTO>> buildResponse(
      UserResponseDTO registeredUser) {
    ResponseEntityBuilder<UserResponseDTO> builder = new ResponseEntityBuilder<>();
    return builder
        .withData(registeredUser)
        .withStatus(HttpStatus.CREATED)
        .withSuccess(true)
        .build();
  }
}

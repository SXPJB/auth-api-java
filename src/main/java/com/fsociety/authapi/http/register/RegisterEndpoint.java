package com.fsociety.authapi.http.register;

import com.fsociety.authapi.app.register.RegisterService;
import com.fsociety.authapi.http.register.dto.UserRequestDTO;
import com.fsociety.authapi.http.register.dto.UserResponseDTO;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** The RegisterEndpoint class is a REST controller that handles the user registration requests. */
@AllArgsConstructor
@RestController
@RequestMapping("/v1/register")
public class RegisterEndpoint {

  private final RegisterService registerService;

  /**
   * Register a new user.
   *
   * @param dto the user registration details
   * @return the response entity with the registered user details
   * @throws Exception if an error occurs during registration this exception will catch by the
   *     global exception handler
   */
  @Operation(
      summary = "Endpoint to register a new user",
      description = "Register a new user and return the user details")
  @PostMapping
  public ResponseEntity<ResponseBody<UserResponseDTO>> register(
      @Valid @RequestBody UserRequestDTO dto) throws Exception {
    UserResponseDTO registeredUser = registerService.register(dto);
    return buildResponse(registeredUser);
  }

  /**
   * Build the response entity with the registered user details.
   *
   * @param dto the registered user details
   * @return the response entity
   */
  private ResponseEntity<ResponseBody<UserResponseDTO>> buildResponse(UserResponseDTO dto) {
    return new ResponseEntityBuilder<UserResponseDTO>()
        .withData(dto)
        .withStatus(HttpStatus.CREATED)
        .withSuccess(true)
        .build();
  }
}

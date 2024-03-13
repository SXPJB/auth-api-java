package com.fsociety.authapi.http.login;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.http.login.dto.LoginRequestDTO;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Login endpoint. */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth")
public class LoginEndpoint {

  private final LoginService loginService;

  /**
   * Authenticate a user.
   *
   * @param dto the login details
   * @return the response entity with the token
   * @throws LoginAuthenticationException if an error occurs during login this exception will catch
   *     by the global exception handler
   */
  @Operation(
      summary = "Endpoint to authenticate a user",
      description = "Authenticate a user and return a token")
  @PostMapping
  public ResponseEntity<ResponseBody<LoginResponseDTO>> login(
      @RequestBody @Valid LoginRequestDTO dto) throws LoginAuthenticationException {
    LoginResponseDTO loginResponse = loginService.login(dto);
    return new ResponseEntityBuilder<LoginResponseDTO>()
        .withData(loginResponse)
        .withStatus(HttpStatus.OK)
        .withSuccess(true)
        .withMessage("Login successful")
        .build();
  }
}

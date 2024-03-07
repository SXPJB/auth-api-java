package com.fsociety.authapi.http.login;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.http.login.dto.LoginRequestDTO;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class LoginEndpoint {

  private final LoginService loginService;

  public LoginEndpoint(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping
  public ResponseEntity<ResponseBody<LoginResponseDTO>> login(
      @RequestBody @Valid LoginRequestDTO dto) throws LoginAuthenticationException {
    LoginResponseDTO loginResponse = loginService.login(dto);
    return new ResponseEntityBuilder<LoginResponseDTO>()
        .withData(loginResponse)
        .withStatus(HttpStatus.OK)
        .withMessage("Login successful")
        .build();
  }
}

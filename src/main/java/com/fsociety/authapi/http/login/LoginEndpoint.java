package com.fsociety.authapi.http.login;

import com.fsociety.authapi.app.login.LoginService;
import com.fsociety.authapi.http.login.dto.LoginResponseDTO;
import com.fsociety.authapi.utils.LoginAuthenticationException;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class LoginEndpoint {

  private final LoginService loginService;

  public LoginEndpoint(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping
  public ResponseEntity<ResponseBody<LoginResponseDTO>> login(
      @RequestBody Map<String, String> loginRequest) throws LoginAuthenticationException {
    LoginResponseDTO loginResponse =
        loginService.login(loginRequest.get("username"), loginRequest.get("password"));
    return new ResponseEntityBuilder<LoginResponseDTO>()
        .withData(loginResponse)
        .withStatus(HttpStatus.OK)
        .withMessage("Login successful")
        .build();
  }
}

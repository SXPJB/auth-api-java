package com.fsociety.authapi.http.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsociety.authapi.http.register.dto.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO extends UserResponseDTO {
  @JsonProperty(value = "token", index = 7)
  private String token;

  @JsonProperty(value = "token_type", index = 8)
  private String tokenType;

  @JsonProperty(value = "expires_in", index = 9)
  private long expiresIn;
}

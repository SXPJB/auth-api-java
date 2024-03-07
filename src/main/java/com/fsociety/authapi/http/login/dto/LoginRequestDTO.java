package com.fsociety.authapi.http.login.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

  @NotNull
  @Size(min = 4, max = 80, message = "Username must be between 4 and 80 characters")
  private String username;

  @NotNull
  @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters")
  private String password;

  @Override
  public String toString() {
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return e.getMessage();
    }
  }
}

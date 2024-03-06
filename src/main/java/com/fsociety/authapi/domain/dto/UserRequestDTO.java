package com.fsociety.authapi.domain.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class UserRequestDTO {
  @Size(min = 4, max = 80, message = "Username must be between 4 and 80 characters")
  private String username;

  @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters")
  private String password;

  @Size(min = 3, max = 80, message = "First name must be between 3 and 80 characters")
  private String firstName;

  @Size(min = 3, max = 80, message = "Last name must be between 3 and 80 characters")
  private String lastName;

  @Size(max = 1)
  private String gender;

  @Email(message = "Invalid email, please provide a valid email address")
  private String email;

  public void setPassword(String password) {
    this.password = encryptPassword(password);
  }

  private String encryptPassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }

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

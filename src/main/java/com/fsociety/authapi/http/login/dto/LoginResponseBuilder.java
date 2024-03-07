package com.fsociety.authapi.http.login.dto;

import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;

public class LoginResponseBuilder {
  LoginResponseDTO loginResponseDTO;

  public LoginResponseBuilder() {
    loginResponseDTO = new LoginResponseDTO();
  }

  public LoginResponseBuilder fromUser(User user) {
    loginResponseDTO.setIdUser(user.getId());
    loginResponseDTO.setUsername(user.getUsername());
    Person person = user.getPerson();
    loginResponseDTO.setIdPerson(person.getId());
    loginResponseDTO.setFirstName(person.getFirstName());
    loginResponseDTO.setLastName(person.getLastName());
    loginResponseDTO.setGender(person.getGender().getCode());
    loginResponseDTO.setEmail(person.getEmail());
    return this;
  }

  public LoginResponseBuilder withToken(String token) {
    loginResponseDTO.setToken(token);
    return this;
  }

  public LoginResponseBuilder withTokenType(String tokenType) {
    loginResponseDTO.setTokenType(tokenType);
    return this;
  }

  public LoginResponseBuilder withTokenType() {
    loginResponseDTO.setTokenType("Bearer");
    return this;
  }

  public LoginResponseBuilder withExpiresIn(long expiresIn) {
    loginResponseDTO.setExpiresIn(expiresIn);
    return this;
  }

  public LoginResponseBuilder withExpiresIn2hours() {
    loginResponseDTO.setExpiresIn(72800);
    return this;
  }

  public LoginResponseDTO build() {
    return loginResponseDTO;
  }
}

package com.fsociety.authapi.domain.dto;

import com.fsociety.authapi.domain.User;

public class UserResponseBuilder {
  private final UserResponseDTO userResponseDTO;

  public UserResponseBuilder() {
    userResponseDTO = new UserResponseDTO();
  }

  public UserResponseBuilder fromUser(User user) {
    userResponseDTO.setIdUser(user.getId());
    userResponseDTO.setIdPerson(user.getPerson().getId());
    userResponseDTO.setUsername(user.getUsername());
    userResponseDTO.setFirstName(user.getPerson().getFirstName());
    userResponseDTO.setLastName(user.getPerson().getLastName());
    userResponseDTO.setGender(user.getPerson().getGender().getCode());
    userResponseDTO.setEmail(user.getPerson().getEmail());
    return this;
  }

  public UserResponseDTO build() {
    return userResponseDTO;
  }
}

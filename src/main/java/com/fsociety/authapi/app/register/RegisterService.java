package com.fsociety.authapi.app.register;

import com.fsociety.authapi.domain.dto.UserRequestDTO;
import com.fsociety.authapi.domain.dto.UserResponseDTO;
import com.fsociety.authapi.utils.RegistrationException;

public interface RegisterService {
  UserResponseDTO register(UserRequestDTO userRegisterDTO) throws RegistrationException;
}

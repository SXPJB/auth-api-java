package com.fsociety.authapi.app.register;

import com.fsociety.authapi.http.register.dto.UserRequestDTO;
import com.fsociety.authapi.http.register.dto.UserResponseDTO;
import com.fsociety.authapi.utils.RegistrationException;

public interface RegisterService {
  UserResponseDTO register(UserRequestDTO userRegisterDTO) throws RegistrationException;
}

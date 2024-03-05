package com.fsociety.authapi.register.app;

import com.fsociety.authapi.register.domain.dto.UserRequestDTO;
import com.fsociety.authapi.register.domain.dto.UserResponseDTO;
import com.fsociety.authapi.utils.RegistrationException;

public interface RegisterService {
    UserResponseDTO register(UserRequestDTO userRegisterDTO) throws RegistrationException;
}

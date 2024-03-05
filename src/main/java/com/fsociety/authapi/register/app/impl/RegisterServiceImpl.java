package com.fsociety.authapi.register.app.impl;

import com.fsociety.authapi.catalog.app.CatalogService;
import com.fsociety.authapi.register.app.RegisterService;
import com.fsociety.authapi.register.domain.PersonRepository;
import com.fsociety.authapi.register.domain.User;
import com.fsociety.authapi.register.domain.UserRepository;
import com.fsociety.authapi.register.domain.dto.UserRequestBuilder;
import com.fsociety.authapi.register.domain.dto.UserRequestDTO;
import com.fsociety.authapi.register.domain.dto.UserResponseBuilder;
import com.fsociety.authapi.register.domain.dto.UserResponseDTO;
import com.fsociety.authapi.utils.NotFoundException;
import com.fsociety.authapi.utils.RegistrationException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service implementation for user registration.
 *
 * @author Emmanuel H. Ramirez (sxpjb)
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    private final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

    private final CatalogService genderService;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public RegisterServiceImpl(CatalogService genderService,
                               UserRepository userRepository,
                               PersonRepository personRepository) {
        this.genderService = genderService;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    /**
     * Register a new user.
     *
     * @param userRegisterDTO the user registration details
     * @return the response entity with the registered user details
     * @throws RegistrationException if an error occurs during registration
     */
    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO userRegisterDTO) throws RegistrationException {
        log.info("Registering user: \n{}", userRegisterDTO);
        try {
            User user = buildUserFromRequest(userRegisterDTO);
            return buildResponseFromUser(user);
        } catch (Exception e) {
            log.error("Error while registering user.", e);
            throw new RegistrationException("Error while registering user: " + e.getMessage(), e.getCause());
        }
    }

    /**
     * Build a user entity from the request details.
     *
     * @param userRegisterDTO the user registration details
     * @return the user entity
     */
    private User buildUserFromRequest(UserRequestDTO userRegisterDTO) throws NotFoundException {
        UserRequestBuilder userBuilder = new UserRequestBuilder();
        User user = userBuilder
                .fromUserRegisterDTO(userRegisterDTO)
                .withGender(userRegisterDTO.getGender(), genderService)
                .build();

        user.setPerson(personRepository.save(user.getPerson()));
        return user;
    }

    /**
     * Build a response entity from the user entity.
     *
     * @param user the user entity
     * @return the response entity
     */
    private UserResponseDTO buildResponseFromUser(User user) {
        return new UserResponseBuilder()
                .fromUser(userRepository.save(user))
                .build();
    }
}
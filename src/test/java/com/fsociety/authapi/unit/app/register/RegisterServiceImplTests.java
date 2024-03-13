package com.fsociety.authapi.unit.app.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.app.emailprovider.EmailService;
import com.fsociety.authapi.app.register.impl.RegisterServiceImpl;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.PersonRepository;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.domain.UserRepository;
import com.fsociety.authapi.http.register.dto.UserRequestBuilder;
import com.fsociety.authapi.http.register.dto.UserRequestDTO;
import com.fsociety.authapi.http.register.dto.UserResponseDTO;
import com.fsociety.authapi.utils.RegistrationException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterServiceImplTests {

  @Mock private EmailService emailService;
  @Mock private CatalogService catalogService;
  @Mock private UserRepository userRepository;
  @Mock private PersonRepository personRepository;
  @InjectMocks private RegisterServiceImpl registerService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    when(catalogService.findByGenderByCode(anyString()))
        .thenReturn(new Catalog(UUID.randomUUID(), "c_catalog", "M", "Male", true));
  }

  private UserRequestDTO buildUserFromRequest() {
    UserRequestDTO userRequestDTO = new UserRequestDTO();
    userRequestDTO.setUsername("username");
    userRequestDTO.setPassword("password");
    userRequestDTO.setFirstName("firstName");
    userRequestDTO.setLastName("lastName");
    userRequestDTO.setEmail("email");
    userRequestDTO.setGender("M");
    return userRequestDTO;
  }

  @Test
  void registerUserSuccessfully() throws Exception {
    UserRequestDTO userRequestDTO = buildUserFromRequest();
    User user =
        new UserRequestBuilder()
            .fromUserRegisterDTO(userRequestDTO)
            .withGender(userRequestDTO.getGender(), catalogService)
            .withExpirationCode()
            .build();
    when(personRepository.save(any())).thenReturn(user.getPerson());
    when(userRepository.save(any())).thenReturn(user);

    UserResponseDTO result = registerService.register(userRequestDTO);

    verify(userRepository).save(any());
    verify(personRepository).save(any());
    verify(emailService).sendConfirmationEmail(anyString(), anyString(), anyString());

    assertNotNull(result);
    assertEquals(user.getUsername(), result.getUsername());
    assertEquals(user.getPerson().getFirstName(), result.getFirstName());
    assertEquals(user.getPerson().getLastName(), result.getLastName());
    assertEquals(user.getPerson().getEmail(), result.getEmail());
    assertEquals(user.getPerson().getGender().getCode(), result.getGender());
  }

  @Test
  void registerUserWithNullRequest() {
    UserRequestDTO userRequestDTO = new UserRequestDTO();
    userRequestDTO.setUsername("123");
    RegistrationException registrationException =
        assertThrows(RegistrationException.class, () -> registerService.register(userRequestDTO));
    assertTrue(registrationException.getMessage().contains("Error while registering user"));
  }

  @Test
  void registerUserWithNullUsername() {
    UserRequestDTO userRequestDTO = buildUserFromRequest();
    userRequestDTO.setUsername(null);
    RegistrationException registrationException =
        assertThrows(RegistrationException.class, () -> registerService.register(userRequestDTO));
    assertTrue(registrationException.getMessage().contains("Error while registering user"));
  }
}

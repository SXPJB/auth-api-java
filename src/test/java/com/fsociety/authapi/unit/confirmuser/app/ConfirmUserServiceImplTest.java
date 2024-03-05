package com.fsociety.authapi.unit.confirmuser.app;

import com.fsociety.authapi.confirmuser.app.impl.ConfirmUserServiceImpl;
import com.fsociety.authapi.register.domain.User;
import com.fsociety.authapi.register.domain.UserRepository;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class ConfirmUserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConfirmUserServiceImpl confirmUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void confirmUser() {
        String username = "username";
        String confirmationCode = "confirmationCode";
        User user = new User();
        user.setUsername(username);
        user.setConfirmationCode(confirmationCode);
        user.setConfirmationCodeExpires(new Timestamp(LocalDateTime.now().plusDays(1).toDateTime().getMillis()));

        when(userRepository.findByUsernameAndConfirmationCodeAndActiveIsTrue(username, confirmationCode))
                .thenReturn(Optional.of(user));

        boolean result = confirmUserService.confirmUser(username, confirmationCode);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);

    }
}

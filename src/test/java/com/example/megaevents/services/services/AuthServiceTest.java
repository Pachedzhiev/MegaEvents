package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.repositories.UserProfileRepository;
import com.example.megaevents.data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthServiceTest extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserProfileRepository userProfileRepository;


    @Autowired
    AuthService authService;


    @Test
    void getUser_whenUserDoesNotExists_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.loadUserByUsername(username));
    }
}

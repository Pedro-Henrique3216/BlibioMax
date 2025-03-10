package com.example.bibliomax.service;

import com.example.bibliomax.exceptions.UserAlreadyRegisteredException;
import com.example.bibliomax.model.Role;
import com.example.bibliomax.model.User;
import com.example.bibliomax.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUserSuccess() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        userService.cadastrarUser("pedro@gmail.com", "12345678", Role.USER);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserFail() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        assertThrows(UserAlreadyRegisteredException.class, () -> userService.cadastrarUser("pedro@gmail.com", "12345678", Role.USER));
    }
}
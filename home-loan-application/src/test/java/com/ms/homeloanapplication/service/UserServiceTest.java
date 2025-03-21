package com.ms.homeloanapplication.service;

import com.ms.homeloanapplication.exception.CustomJwtException;
import com.ms.homeloanapplication.model.Role;
import com.ms.homeloanapplication.model.entity.User;
import com.ms.homeloanapplication.repository.UserRepository;
import com.ms.homeloanapplication.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void getAllUsers() {
        List<User> users = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    void signUp_Success() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("token");
        when(userRepository.save(any())).thenReturn(user);

        String result = userService.signUp(user, false);

        assertNotNull(result);
        assertEquals("token", result);
        verify(userRepository).save(any());
    }

    @Test
    void signUp_UserExists() {
        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        assertThrows(CustomJwtException.class, () -> userService.signUp(user, false));
        verify(userRepository, never()).save(any());
    }

    @Test
    void signUp_AsAdmin() {
        User user = new User();
        user.setUsername("adminUser");
        user.setPassword("password");

        when(userRepository.existsByUsername("adminUser")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("token");
        when(userRepository.save(any())).thenReturn(user);

        String result = userService.signUp(user, true);

        assertNotNull(result);
        assertEquals("token", result);
        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
    }

    @Test
    void delete_Success() {
        User user = new User();
        user.setUsername("testUser");
        user.setRoles(Collections.singletonList(Role.ROLE_CLIENT));

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        doNothing().when(userRepository).deleteByUsername("testUser");

        assertDoesNotThrow(() -> userService.delete("testUser"));
        verify(userRepository).deleteByUsername("testUser");
    }

    @Test
    void search_Success() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = userService.search("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }
}
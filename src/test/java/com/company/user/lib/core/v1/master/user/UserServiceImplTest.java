package com.company.user.lib.core.v1.master.user;

import com.company.user.lib.core.v1.master.user.dto.UserDto;
import com.company.user.lib.core.v1.master.user.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepo, passwordEncoder);
    }

    @Test
    void register_ShouldCreateUser_WhenEmailNotExists() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setNama("Coba User");
        request.setEmail("user@coba.com");
        request.setNoHp("081234567890");
        request.setPassword("secret");

        when(userRepo.existsByEmail("user@coba.com")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("hashed-secret");

        User savedUser = User.builder()
                .id("uuid-123")
                .nama("Coba User")
                .email("user@coba.com")
                .noHp("081234567890")
                .password("hashed-secret")
                .build();

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.register(request);

        assertNotNull(result);
        assertEquals("uuid-123", result.getId());
        assertEquals("Coba User", result.getNama());
        assertEquals("user@coba.com", result.getEmail());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());
        assertEquals("hashed-secret", userCaptor.getValue().getPassword());
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        when(userRepo.existsByEmail("existing@example.com")).thenReturn(true);

        UserRegisterRequest request = new UserRegisterRequest();
        request.setEmail("existing@example.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(request);
        });
        assertEquals("Email sudah digunakan", ex.getMessage());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = User.builder()
                .id("uuid-123")
                .nama("User New")
                .email("user@new.com")
                .build();

        when(userRepo.findById("uuid-123")).thenReturn(Optional.of(user));

        User result = userService.findById("uuid-123");

        assertNotNull(result);
        assertEquals("user@new.com", result.getEmail());
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepo.findById("unknown")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.findById("unknown");
        });
        assertEquals("User tidak ditemukan", ex.getMessage());
    }
}
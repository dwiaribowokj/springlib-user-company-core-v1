package com.company.user.lib.core.v1.master.user;

import com.company.user.lib.core.v1.master.user.dto.UserDto;
import com.company.user.lib.core.v1.master.user.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto register(UserRegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan");
        }

        User user = User.builder()
                .nama(request.getNama())
                .noHp(request.getNoHp())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepo.save(user);
        return UserDto.fromEntity(saved);
    }

    @Override
    public User findById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));
    }
}

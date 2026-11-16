package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.RegisterRequest;
import com.mayara.cashflow.dto.UserResponse;
import com.mayara.cashflow.model.User;
import com.mayara.cashflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário com esse e-mail.");
        }

        String passwordHash = passwordEncoder.encode(request.password());
        User user = new User(request.name(), email, passwordHash);

        return UserResponse.from(userRepository.save(user));
    }
}
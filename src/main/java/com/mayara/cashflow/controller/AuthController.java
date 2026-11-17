package com.mayara.cashflow.controller;

import com.mayara.cashflow.dto.LoginRequest;
import com.mayara.cashflow.dto.LoginResponse;
import com.mayara.cashflow.dto.RegisterRequest;
import com.mayara.cashflow.dto.UserResponse;
import com.mayara.cashflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        Long id = Long.valueOf(jwt.getSubject());
        String name = jwt.getClaimAsString("name");
        String email = jwt.getClaimAsString("email");

        return new UserResponse(id, name, email);
    }
}


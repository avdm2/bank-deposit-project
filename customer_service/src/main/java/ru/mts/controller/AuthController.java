package ru.mts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.dto.LoginRequest;
import ru.mts.dto.LoginResponse;
import ru.mts.dto.RegisterRequest;
import ru.mts.dto.RegisterResponse;
import ru.mts.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

        log.info(">> Регистрация пользователя {}", registerRequest.getUsername());
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        log.info(">> Авторизация пользователя {}", loginRequest.getUsername());
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}

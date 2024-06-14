package ru.mts.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mts.common.entity.Customer;
import ru.mts.common.exception.NotFoundException;
import ru.mts.common.exception.RequireFieldsException;
import ru.mts.common.repository.CustomerRepository;
import ru.mts.common.service.JwtService;
import ru.mts.dto.LoginRequest;
import ru.mts.dto.LoginResponse;
import ru.mts.dto.RegisterResponse;
import ru.mts.exception.UsernameAlreadyExistsException;
import ru.mts.dto.RegisterRequest;

@Service
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (registerRequest == null || registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getPhoneNumber() == null) {
            throw new RequireFieldsException("Заполните логин, номер телефона и пароль");
        }

        if (customerRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Пользователь с логином [" + registerRequest.getUsername() + "] уже существует");
        }

        Customer customer = new Customer()
                .setUsername(registerRequest.getUsername())
                .setPhoneNumber(registerRequest.getPhoneNumber())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        customerRepository.save(customer);

        return new RegisterResponse().setUserId(customer.getId()).setUsername(customer.getUsername());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new RequireFieldsException("Заполните логин и пароль");
        }

        Customer customer = customerRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь с логином [" + loginRequest.getUsername() + "] не найден"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String token = jwtService.generateToken(customer);

        return new LoginResponse().setAccessToken(token);
    }
}

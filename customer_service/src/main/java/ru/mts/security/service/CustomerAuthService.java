package ru.mts.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mts.security.repository.CustomerRepository;

@Service
public class CustomerAuthService {

    private final CustomerRepository customerRepository;

    public CustomerAuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UserDetailsService userDetailsService() {
        return username -> customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

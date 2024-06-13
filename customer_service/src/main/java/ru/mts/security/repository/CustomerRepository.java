package ru.mts.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.security.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUsername(String username);
}

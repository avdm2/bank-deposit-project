package ru.mts.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.common.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUsername(String username);
}

package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.entity.Deposit;

import java.util.List;
import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

    List<Deposit> findAllByCustomerUsername(String username);
    Optional<Deposit> findById(Integer id);
}

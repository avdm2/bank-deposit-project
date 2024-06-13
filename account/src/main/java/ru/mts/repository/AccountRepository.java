package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.common.entity.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByNum(BigDecimal num);
}

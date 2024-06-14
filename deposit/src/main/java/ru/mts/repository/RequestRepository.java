package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.common.entity.Customer;
import ru.mts.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByCustomer(Customer customer);
}

package ru.mts.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "bank_accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank_accounts")
    private Integer id;

    @Column(name = "num_bank_accounts", nullable = false, precision = 20, scale = 0, unique = true)
    private BigDecimal num;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}

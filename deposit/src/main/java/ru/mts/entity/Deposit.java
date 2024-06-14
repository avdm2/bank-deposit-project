package ru.mts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mts.common.entity.Account;
import ru.mts.common.entity.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "deposits")
@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposit")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "deposit_account_id", nullable = false)
    private Account depositAccount;

    @Column(name = "deposit_refill", nullable = false)
    private boolean refill;

    @Column(name = "deposit_withdrawal", nullable = false)
    private boolean withdrawal;

    @Column(name = "deposits_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "deposit_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rate;

    @Column(name = "percent_payment_date", nullable = false)
    private LocalDateTime percentPaymentDate;

    @Column(name = "capitalization", nullable = false)
    private boolean capitalization;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositStatus status;
}

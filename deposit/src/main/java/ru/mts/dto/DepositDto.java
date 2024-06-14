package ru.mts.dto;

import lombok.Data;
import ru.mts.entity.Deposit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositDto {

    private Integer id;

    private String customerUsername;
    private BigDecimal accountNumber;
    private BigDecimal amount;

    private boolean refill;
    private boolean withdrawal;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime percentPaymentDate;
    private BigDecimal rate;
    private boolean capitalization;

    private String status;

    public DepositDto(Deposit deposit) {
        this.id = deposit.getId();
        this.customerUsername = deposit.getCustomer().getUsername();
        this.accountNumber = deposit.getDepositAccount().getNum();
        this.amount = deposit.getAmount();
        this.refill = deposit.isRefill();
        this.withdrawal = deposit.isWithdrawal();
        this.startDate = deposit.getStartDate();
        this.endDate = deposit.getEndDate();
        this.percentPaymentDate = deposit.getPercentPaymentDate();
        this.rate = deposit.getRate();
        this.capitalization = deposit.isCapitalization();
        this.status = deposit.getStatus().name();
    }
}


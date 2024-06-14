package ru.mts.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DepositCreationRequest {

    private BigDecimal accountNumber;
    private BigDecimal amount;
    private boolean refill;
    private boolean withdrawal;
    private LocalDateTime endDate;
    private LocalDateTime percentPaymentDate;
    private boolean capitalization;
}

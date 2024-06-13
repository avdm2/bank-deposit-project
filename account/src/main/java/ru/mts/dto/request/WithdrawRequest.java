package ru.mts.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class WithdrawRequest {

    private BigDecimal num;
    private BigDecimal amount;
}

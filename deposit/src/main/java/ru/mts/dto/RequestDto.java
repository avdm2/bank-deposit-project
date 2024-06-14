package ru.mts.dto;

import lombok.Data;
import ru.mts.entity.Request;

import java.time.LocalDateTime;

@Data
public class RequestDto {

    private Integer id;
    private Integer customerId;
    private Integer depositId;
    private String status;
    private LocalDateTime requestDate;
    private LocalDateTime updateDate;

    public RequestDto(Request request) {
        this.id = request.getId();
        this.customerId = request.getCustomer().getId();
        this.depositId = request.getDeposit().getId();
        this.status = request.getStatus();
        this.requestDate = request.getRequestDate();
        this.updateDate = request.getUpdateDate();
    }
}

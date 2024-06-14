package ru.mts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.dto.DepositDto;
import ru.mts.dto.request.DepositCreationRequest;
import ru.mts.service.DepositService;

import java.util.List;

@RestController
@RequestMapping("/api/deposit")
@Slf4j
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/create")
    public ResponseEntity<DepositDto> createDeposit(@RequestBody DepositCreationRequest depositCreationRequest,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} создал вклад на сумму {}", userDetails.getUsername(), depositCreationRequest.getAmount());
        return ResponseEntity.ok(depositService.createDeposit(depositCreationRequest, userDetails.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepositDto>> getAllDeposits(@AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} посмотрел информацию обо всех вкладах", userDetails.getUsername());
        return ResponseEntity.ok(depositService.getDeposits(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositDto> getDepositById(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} посмотрел информацию о вкладе {}", userDetails.getUsername(), id);
        return ResponseEntity.ok(depositService.getDepositById(id, userDetails.getUsername()));
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<DepositDto> closeDeposit(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} закрыл вклад {}", userDetails.getUsername(), id);
        return ResponseEntity.ok(depositService.closeDeposit(id, userDetails.getUsername()));
    }
}

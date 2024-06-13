package ru.mts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.dto.AccountDto;
import ru.mts.dto.request.CreationRequest;
import ru.mts.dto.request.DepositRequest;
import ru.mts.dto.request.WithdrawRequest;
import ru.mts.service.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> create(@RequestBody CreationRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/{num}")
    public ResponseEntity<AccountDto> get(@PathVariable BigDecimal num) {
        return ResponseEntity.ok(accountService.getAccount(num));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositRequest request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(accountService.withdraw(request));
    }
}

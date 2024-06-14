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
import ru.mts.dto.AccountDto;
import ru.mts.dto.request.CreationRequest;
import ru.mts.dto.request.DepositRequest;
import ru.mts.dto.request.WithdrawRequest;
import ru.mts.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> create(@RequestBody CreationRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} создал аккаунт с номером {}", userDetails.getUsername(), request.getNum());
        return ResponseEntity.ok(accountService.createAccount(request, userDetails.getUsername()));
    }

    @GetMapping("/{num}")
    public ResponseEntity<AccountDto> get(@PathVariable BigDecimal num,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} посмотрел информацию об аккаунте {}", userDetails.getUsername(), num);
        return ResponseEntity.ok(accountService.getAccount(num, userDetails.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        log.info(">> {} посмотрел информацию обо всех аккаунтах", userDetails.getUsername());
        return ResponseEntity.ok(accountService.getAll(userDetails.getUsername()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositRequest request,
                                              @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} внес {} на аккаунт {}", userDetails.getUsername(), request.getAmount(), request.getNum());
        return ResponseEntity.ok(accountService.deposit(request, userDetails.getUsername()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestBody WithdrawRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> {} вывел {} с аккаунта {}", userDetails.getUsername(), request.getAmount(), request.getNum());
        return ResponseEntity.ok(accountService.withdraw(request, userDetails.getUsername()));
    }
}

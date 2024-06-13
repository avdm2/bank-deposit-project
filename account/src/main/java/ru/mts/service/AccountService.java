package ru.mts.service;

import org.springframework.stereotype.Service;
import ru.mts.common.entity.Account;
import ru.mts.dto.AccountDto;
import ru.mts.dto.request.CreationRequest;
import ru.mts.dto.request.DepositRequest;
import ru.mts.dto.request.WithdrawRequest;
import ru.mts.exception.AccountAlreadyExistsException;
import ru.mts.exception.NotEnoughFundsException;
import ru.mts.exception.NotFoundException;
import ru.mts.exception.RequireFieldsException;
import ru.mts.mapper.AccountMapper;
import ru.mts.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDto createAccount(CreationRequest request) {
        if (request == null || request.getNum() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта");
        }

        Optional<Account> accountOptional = accountRepository.findByNum(request.getNum());
        if (accountOptional.isPresent()) {
            throw new AccountAlreadyExistsException("Аккаунт с номером " + request.getNum() + " уже существует");
        }

        Account account = new Account().setNum(request.getNum()).setAmount(BigDecimal.ZERO);
        accountRepository.save(account);

        return accountMapper.transform(account);
    }

    public AccountDto deposit(DepositRequest request) {
        if (request == null || request.getNum() == null || request.getAmount() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта и сумму");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequireFieldsException("Сумма должна быть больше нуля");
        }

        Optional<Account> accountOptional = accountRepository.findByNum(request.getNum());
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Аккаунт с номером " + request.getNum() + " не найден");
        }

        Account account = accountOptional.get();
        account.setAmount(account.getAmount().add(request.getAmount()));
        accountRepository.save(account);

        return accountMapper.transform(account);
    }

    public AccountDto withdraw(WithdrawRequest request) {
        if (request == null || request.getNum() == null || request.getAmount() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта и сумму");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequireFieldsException("Сумма должна быть больше нуля");
        }

        Optional<Account> accountOptional = accountRepository.findByNum(request.getNum());
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Аккаунт с номером " + request.getNum() + " не найден");
        }

        Account account = accountOptional.get();
        if (account.getAmount().compareTo(request.getAmount()) < 0) {
            throw new NotEnoughFundsException("На аккаунте недостаточно средств");
        }

        account.setAmount(account.getAmount().subtract(request.getAmount()));
        accountRepository.save(account);

        return accountMapper.transform(account);
    }

    public AccountDto getAccount(BigDecimal num) {
        Optional<Account> accountOptional = accountRepository.findByNum(num);
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Аккаунт с номером " + num + " не найден");
        }

        return accountMapper.transform(accountOptional.get());
    }
}

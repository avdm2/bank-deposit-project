package ru.mts.service;

import org.springframework.stereotype.Service;
import ru.mts.common.entity.Account;
import ru.mts.common.entity.Customer;
import ru.mts.common.repository.CustomerRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.customerRepository = customerRepository;
    }

    public AccountDto createAccount(CreationRequest request, String username) {
        if (request == null || request.getNum() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта");
        }

        Optional<Account> accountOptional = accountRepository.findByNum(request.getNum());
        if (accountOptional.isPresent()) {
            throw new AccountAlreadyExistsException("Аккаунт с номером " + request.getNum() + " уже существует");
        }

        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        Account account = new Account().setNum(request.getNum()).setAmount(BigDecimal.ZERO).setCustomer(customer);
        accountRepository.save(account);

        if (customer.getAccounts() == null) {
            customer.setAccounts(new HashSet<>());
        }
        customer.getAccounts().add(account);
        customerRepository.save(customer);

        return accountMapper.transform(account);
    }

    public AccountDto deposit(DepositRequest request, String username) {
        if (request == null || request.getNum() == null || request.getAmount() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта и сумму");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequireFieldsException("Сумма должна быть больше нуля");
        }

        Account account = checkAccount(request.getNum(), username);
        account.setAmount(account.getAmount().add(request.getAmount()));
        accountRepository.save(account);

        return accountMapper.transform(account);
    }

    public AccountDto withdraw(WithdrawRequest request, String username) {
        if (request == null || request.getNum() == null || request.getAmount() == null) {
            throw new RequireFieldsException("Заполните номер аккаунта и сумму");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequireFieldsException("Сумма должна быть больше нуля");
        }

        Account account = checkAccount(request.getNum(), username);
        if (account.getAmount().compareTo(request.getAmount()) < 0) {
            throw new NotEnoughFundsException("На аккаунте недостаточно средств");
        }

        account.setAmount(account.getAmount().subtract(request.getAmount()));
        accountRepository.save(account);

        return accountMapper.transform(account);
    }

    public AccountDto getAccount(BigDecimal num, String username) {
        return accountMapper.transform(checkAccount(num, username));
    }

    public List<AccountDto> getAll(String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        return customer.getAccounts().stream().map(accountMapper::transform).collect(Collectors.toList());
    }

    public Account checkAccount(BigDecimal num, String username) {
        Optional<Account> accountOptional = accountRepository.findByNum(num);
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Аккаунт с номером " + num + " не найден");
        }

        Account account = accountOptional.get();
        if (!account.getCustomer().getUsername().equals(username)) {
            throw new NotFoundException("Аккаунт с номером " + num + " не принадлежит пользователю " + username);
        }

        return account;
    }
}

package ru.mts.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.common.entity.Account;
import ru.mts.common.entity.Customer;
import ru.mts.common.repository.CustomerRepository;
import ru.mts.dto.DepositDto;
import ru.mts.dto.request.DepositCreationRequest;
import ru.mts.entity.Deposit;
import ru.mts.entity.DepositStatus;
import ru.mts.exception.DepositDeletingException;
import ru.mts.exception.IllegalDepositFundsException;
import ru.mts.exception.NotEnoughFundsException;
import ru.mts.exception.NotFoundException;
import ru.mts.repository.AccountRepository;
import ru.mts.repository.DepositRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepositService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final DepositRepository depositRepository;
    private final RequestService requestService;

    private final static BigDecimal[] RATES = {new BigDecimal("0.05"), new BigDecimal("0.1"), new BigDecimal("0.15")};

    @Transactional
    public DepositDto createDeposit(DepositCreationRequest depositCreationRequest, String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        Account account = accountRepository.findByNum(depositCreationRequest.getAccountNumber()).orElseThrow(() -> new NotFoundException("Аккаунт с номером " + depositCreationRequest.getAccountNumber() + " не найден"));

        if (!account.getCustomer().getUsername().equals(username)) {
            throw new NotFoundException("Аккаунт с номером " + account.getNum() + " не принадлежит пользователю " + username);
        }

        if (depositCreationRequest.getAmount().compareTo(account.getAmount()) > 0) {
            throw new NotEnoughFundsException("Недостаточно средств на счете");
        }

        if (depositCreationRequest.getAmount().compareTo(BigDecimal.valueOf(10000)) <= 0) {
            throw new IllegalDepositFundsException("Сумма вклада должна быть больше 10000");
        }

        account.setAmount(account.getAmount().subtract(depositCreationRequest.getAmount()));
        accountRepository.save(account);

        Random rnd = new Random();
        BigDecimal rate = RATES[rnd.nextInt(RATES.length)];

        Deposit deposit = new Deposit()
                .setCustomer(customer)
                .setDepositAccount(account)
                .setRefill(depositCreationRequest.isRefill())
                .setWithdrawal(depositCreationRequest.isWithdrawal())
                .setAmount(depositCreationRequest.getAmount())
                .setStartDate(LocalDateTime.now())
                .setEndDate(depositCreationRequest.getEndDate())
                .setRate(rate)
                .setCapitalization(depositCreationRequest.isCapitalization())
                .setPercentPaymentDate(depositCreationRequest.getPercentPaymentDate())
                .setStatus(DepositStatus.CREATED);

        Deposit savedDeposit = depositRepository.save(deposit);
        Deposit proceededDeposit = requestService.proceedDeposit(savedDeposit);
        if (proceededDeposit.getStatus() == DepositStatus.CLOSED) {
            Account accountToRefund = proceededDeposit.getDepositAccount();
            accountToRefund.setAmount(accountToRefund.getAmount().add(proceededDeposit.getAmount()));
            accountRepository.save(accountToRefund);
        }

        return new DepositDto(proceededDeposit);
    }

    public List<DepositDto> getDeposits(String username) {
        customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        List<Deposit> deposits = depositRepository.findAllByCustomerUsername(username);
        return deposits.stream().map(DepositDto::new).collect(Collectors.toList());
    }

    public DepositDto getDepositById(Integer id, String username) {
        return new DepositDto(getDepositByIdAndUser(id, username));
    }

    @Transactional
    public DepositDto closeDeposit(Integer id, String username) {
        Deposit deposit = getDepositByIdAndUser(id, username);
        if (deposit.getStatus() == DepositStatus.CLOSED) {
            throw new DepositDeletingException("Нельзя удалить закрытый вклад");
        }

        deposit.setStatus(DepositStatus.CLOSED);
        Deposit savedDeposit = depositRepository.save(deposit);

        Account account = savedDeposit.getDepositAccount();
        account.setAmount(account.getAmount().add(savedDeposit.getAmount()));
        accountRepository.save(account);

        return new DepositDto(savedDeposit);
    }

    private Deposit getDepositByIdAndUser(Integer id, String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        Optional<Deposit> optionalDeposit = depositRepository.findById(id);
        if (optionalDeposit.isEmpty() || !optionalDeposit.get().getCustomer().getId().equals(customer.getId())) {
            throw new NotFoundException("Вклад с ID " + id + " не принадлежит пользователю " + username);
        }
        return optionalDeposit.get();
    }
}

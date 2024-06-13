package ru.mts.mapper;

import org.springframework.stereotype.Service;
import ru.mts.common.entity.Account;
import ru.mts.dto.AccountDto;

@Service
public class AccountMapper {

    public AccountDto transform(Account account) {
        return new AccountDto().setNum(account.getNum()).setAmount(account.getAmount());
    }

    public Account toDomain(AccountDto accountDto) {
        return new Account().setNum(accountDto.getNum()).setAmount(accountDto.getAmount());
    }
}

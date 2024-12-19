package com.hoppr.bankaccount.domain.usecases.movements.credit;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import com.hoppr.bankaccount.domain.model.Amount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditAccountUseCase {

    private final AccountRepository accountRepository;

    public Account accept(Long id, Float amount) {
        var account = accountRepository.get(id);
        account.credit(Amount.of(amount));
        return accountRepository.save(account);
    }

}

package com.hoppr.bankaccount.domain.usecases.movements.debit;

import com.hoppr.bankaccount.domain.model.Amount;
import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebitAccountUseCase {

    private final AccountRepository accountRepository;

    public Account accept(Long id, Float amount) {
        var account = accountRepository.get(id);
        account.debit(Amount.of(amount));
        return accountRepository.save(account);
    }
}

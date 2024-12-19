package com.hoppr.bankaccount.domain.usecases.movements.withdraw;

import com.hoppr.bankaccount.domain.model.Amount;
import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawUseCase {

    private final AccountRepository accountRepository;

    public Account accept(Long id, Float amount) {
        var account = accountRepository.get(id);
        account.withdraw(Amount.of(amount));
        return accountRepository.save(account);
    }

}

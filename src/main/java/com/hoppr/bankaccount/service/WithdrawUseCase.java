package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.entity.Amount;
import com.hoppr.bankaccount.repository.AccountRepository;
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

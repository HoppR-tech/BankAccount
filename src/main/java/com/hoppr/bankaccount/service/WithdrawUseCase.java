package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.entity.Amount;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawUseCase {

    private final AccountRepository accountRepository;

    public Account accept(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        account.withdraw(Amount.of(amount));
        return accountRepository.save(account);
    }

}

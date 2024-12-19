package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountIsClosed;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreditAccountUseCase {

    private final AccountRepository accountRepository;

    public Account accept(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        account.setAmount(
                BigDecimal.valueOf(amount).add(BigDecimal.valueOf(account.getAmount())).floatValue());

        return accountRepository.save(account);
    }

    private void checkClosedAccount(Account account) {
        if (account.isClosed()) {
            throw new AccountIsClosed("Cannot perform operation on closed account");
        }
    }

    void checkValidAmount(Float amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

}

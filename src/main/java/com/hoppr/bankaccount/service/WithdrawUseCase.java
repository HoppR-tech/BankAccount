package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountClosedException;
import com.hoppr.bankaccount.exception.NotEnoughMoneyException;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class WithdrawUseCase {

    private final AccountRepository accountRepository;

    public Account withdrawMoney(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        if (amount > account.getAmount()) {
            throw new NotEnoughMoneyException("Not enough money on the account");
        }

        return debitAccount(id, amount);
    }

    public Account debitAccount(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        account.setAmount(
                BigDecimal.valueOf(account.getAmount()).subtract(BigDecimal.valueOf(amount)).floatValue());

        return accountRepository.save(account);
    }

    private void checkClosedAccount(Account account) {
        if (account.isClosed()) {
            throw new AccountClosedException("Cannot perform operation on closed account");
        }
    }

    void checkValidAmount(Float amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}

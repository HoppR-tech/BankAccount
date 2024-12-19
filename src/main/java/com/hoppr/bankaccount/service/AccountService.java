package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.exception.AccountIsClosed;
import com.hoppr.bankaccount.exception.AccountIsNotClosed;
import com.hoppr.bankaccount.exception.NotEnoughMoney;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Deprecated
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Deprecated
    public Account creditAccount(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        account.setAmount(
                BigDecimal.valueOf(amount).add(BigDecimal.valueOf(account.getAmount())).floatValue());

        return accountRepository.save(account);
    }

    @Deprecated
    public Account debitAccount(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        account.setAmount(
                BigDecimal.valueOf(account.getAmount()).subtract(BigDecimal.valueOf(amount)).floatValue());

        return accountRepository.save(account);
    }

    @Deprecated
    public Account withdrawMoney(Long id, Float amount) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkClosedAccount(account);
        checkValidAmount(amount);

        if (amount > account.getAmount()) {
            throw new NotEnoughMoney("Not enough money on the account");
        }

        return debitAccount(id, amount);
    }

    void checkValidAmount(Float amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    @Deprecated
    public void closeAccount(Long id) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (account.isClosed()) {
            throw new AccountIsAlreadyClosed("Account already closed");
        }

        account.setClosed(true);
        accountRepository.save(account);
    }

    private void checkClosedAccount(Account account) {
        if (account.isClosed()) {
            throw new AccountIsClosed("Cannot perform operation on closed account");
        }
    }

    @Deprecated
    public void reopenAccount(Long id) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!account.isClosed()) {
            throw new AccountIsNotClosed("Account is not closed");
        }

        account.setClosed(false);
        accountRepository.save(account);
    }
}

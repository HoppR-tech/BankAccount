package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.exception.AccountIsNotClosed;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReopenAccountUseCase {

    private final AccountRepository accountRepository;

    public void accept(Long id) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!account.isClosed()) {
            throw new AccountIsNotClosed("Account is not closed");
        }

        account.setClosed(false);
        accountRepository.save(account);
    }

}

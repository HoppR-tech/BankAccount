package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CloseAccountUseCase {

    private final AccountRepository accountRepository;

    public void accept(Long id) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (account.isClosed()) {
            throw new AccountIsAlreadyClosed("Account already closed");
        }

        account.setClosed(true);
        accountRepository.save(account);
    }
}

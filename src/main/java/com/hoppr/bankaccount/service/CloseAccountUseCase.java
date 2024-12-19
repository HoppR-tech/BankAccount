package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CloseAccountUseCase {

    private final AccountRepository accountRepository;

    public void accept(Long id) {
        var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        account.close();
        accountRepository.save(account);
    }
}

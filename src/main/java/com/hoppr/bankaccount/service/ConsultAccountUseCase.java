package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsultAccountUseCase {

    private final AccountRepository accountRepository;

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}

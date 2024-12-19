package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultAccountUseCase {

    private final AccountRepository accountRepository;

    public Account getAccount(Long id) {
        return accountRepository.get(id);
    }

}

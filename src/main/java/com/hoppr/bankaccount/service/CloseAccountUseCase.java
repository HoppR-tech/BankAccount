package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloseAccountUseCase {

    private final AccountRepository accountRepository;

    public void accept(Long id) {
        var account = accountRepository.get(id);
        account.close();
        accountRepository.save(account);
    }
}

package com.hoppr.bankaccount.domain.usecases.close;

import com.hoppr.bankaccount.domain.model.AccountRepository;
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

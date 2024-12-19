package com.hoppr.bankaccount.domain.usecases.reopen;

import com.hoppr.bankaccount.domain.model.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReopenAccountUseCase {

    private final AccountRepository accountRepository;

    public void accept(Long id) {
        var account = accountRepository.get(id);
        account.reOpen();
        accountRepository.save(account);
    }

}

package com.hoppr.bankaccount.domain.usecases.consult;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
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

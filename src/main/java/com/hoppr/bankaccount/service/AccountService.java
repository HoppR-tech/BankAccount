package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final ConsultAccountUseCase consultAccountUseCase;
    private final CreditAccountUseCase creditAccountUseCase;
    private final DebitAccountUseCase debitAccountUseCase;
    private final WithdrawUseCase withdrawUseCase;
    private final CloseAccountUseCase closeAccountUseCase;
    private final ReopenAccountUseCase reopenAccountUseCase;

    @Deprecated
    public Account getAccount(Long id) {
        return consultAccountUseCase.getAccount(id);
    }

    @Deprecated
    public Account creditAccount(Long id, Float amount) {
        return creditAccountUseCase.accept(id, amount);
    }

    @Deprecated
    public Account debitAccount(Long id, Float amount) {
        return debitAccountUseCase.accept(id, amount);
    }

    @Deprecated
    public Account withdrawMoney(Long id, Float amount) {
        return withdrawUseCase.accept(id, amount);
    }

    @Deprecated
    public void closeAccount(Long id) {
        closeAccountUseCase.accept(id);
    }

    @Deprecated
    public void reopenAccount(Long id) {
        reopenAccountUseCase.accept(id);
    }
}

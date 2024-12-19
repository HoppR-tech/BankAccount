package com.hoppr.bankaccount;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.usecases.reopen.AccountIsNotClosed;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import com.hoppr.bankaccount.domain.usecases.reopen.ReopenAccountUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReopenAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ReopenAccountUseCase useCase;

    @Test
    void account_is_not_closed_yet() {
        when(accountRepository.get(1L))
                .thenReturn(Account.builder()
                        .id(1L)
                        .closed(false)
                        .build());

        assertThatExceptionOfType(AccountIsNotClosed.class)
                .isThrownBy(() -> useCase.accept(1L))
                .withMessage("Account is not closed");
    }

    @Test
    void account_is_reopened() {
        when(accountRepository.get(1L))
                .thenReturn(Account.builder()
                        .id(1L)
                        .closed(true)
                        .build());

        useCase.accept(1L);

        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(argument.capture());
        Account actualAccount = argument.getValue();

        assertThat(actualAccount)
                .isEqualTo(Account.builder()
                        .id(1L)
                        .closed(false)
                        .build());
    }

}
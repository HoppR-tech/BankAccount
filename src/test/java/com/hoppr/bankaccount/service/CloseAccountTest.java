package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloseAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CloseAccountUseCase useCase;

    @Test
    void account_is_already_closed() {
        when(accountRepository.get(1L))
                .thenReturn(Account.builder()
                        .id(1L)
                        .closed(true)
                        .build());

        assertThatExceptionOfType(AccountIsAlreadyClosed.class)
                .isThrownBy(() -> useCase.accept(1L))
                .withMessage("Account already closed");
    }

    @Test
    void close_account() {
        when(accountRepository.get(1L))
                .thenReturn(Account.builder()
                        .id(1L)
                        .closed(false)
                        .build());

        useCase.accept(1L);

        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(argument.capture());
        Account actualAccount = argument.getValue();

        assertThat(actualAccount)
                .isEqualTo(Account.builder()
                        .id(1L)
                        .closed(true)
                        .build());
    }
}
package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountNotClosedException;
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
class ReopenAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ReopenAccountUseCase useCase;

    @Test
    void account_is_not_closed_yet() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(Account.builder()
                        .id(1L)
                        .closed(false)
                        .build()));

        assertThatExceptionOfType(AccountNotClosedException.class)
                .isThrownBy(() -> useCase.accept(1L))
                .withMessage("Account is not closed");
    }

    @Test
    void account_is_reopened() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(Account.builder()
                        .id(1L)
                        .closed(true)
                        .build()));

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
package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountAlreadyClosedException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlockAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void unblockedAccountShouldBeBlocked() {
        // Given
        var argumentCaptor = ArgumentCaptor.forClass(Account.class);
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        // When
        accountService.closeAccount(1L);
        verify(accountRepository).save(argumentCaptor.capture());

        // Then
        assertThat(argumentCaptor.getValue().isClosed()).isTrue();
    }

    @Test
    void blockAccountWhenAlreadyBlockedShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> accountService.closeAccount(1L))
                .isInstanceOf(AccountAlreadyClosedException.class);
    }

    @Test
    void blockedAccountShouldBeUnblocked() {
        // Given
        var argumentCaptor = ArgumentCaptor.forClass(Account.class);
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        // When
        accountService.reopenAccount(1L);
        verify(accountRepository).save(argumentCaptor.capture());

        // Then
        assertThat(argumentCaptor.getValue().isClosed()).isFalse();
    }

    @Test
    void unblockAccountWhenAlreadyUnblockedShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(false).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> accountService.reopenAccount(1L))
                .isInstanceOf(AccountNotClosedException.class);
    }
}

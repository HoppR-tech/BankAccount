package com.hoppr.bankaccount;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.usecases.close.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.domain.usecases.reopen.AccountIsNotClosed;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import com.hoppr.bankaccount.domain.usecases.close.CloseAccountUseCase;
import com.hoppr.bankaccount.domain.usecases.reopen.ReopenAccountUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlockAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CloseAccountUseCase closeAccountUseCase;
    @InjectMocks
    private ReopenAccountUseCase reopenAccountUseCase;

    @Test
    void unblockedAccountShouldBeBlocked() {
        // Given
        var argumentCaptor = ArgumentCaptor.forClass(Account.class);
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.get(1L)).thenReturn(savedAccount);

        // When
        closeAccountUseCase.accept(1L);
        verify(accountRepository).save(argumentCaptor.capture());

        // Then
        assertThat(argumentCaptor.getValue().closed()).isTrue();
    }

    @Test
    void blockAccountWhenAlreadyBlockedShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
        when(accountRepository.get(1L)).thenReturn(savedAccount);

        assertThatThrownBy(() -> closeAccountUseCase.accept(1L))
                .isInstanceOf(AccountIsAlreadyClosed.class);
    }

    @Test
    void blockedAccountShouldBeUnblocked() {
        // Given
        var argumentCaptor = ArgumentCaptor.forClass(Account.class);
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
        when(accountRepository.get(1L)).thenReturn(savedAccount);

        // When
        reopenAccountUseCase.accept(1L);
        verify(accountRepository).save(argumentCaptor.capture());

        // Then
        assertThat(argumentCaptor.getValue().closed()).isFalse();
    }

    @Test
    void unblockAccountWhenAlreadyUnblockedShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).closed(false).build();
        when(accountRepository.get(1L)).thenReturn(savedAccount);

        assertThatThrownBy(() -> reopenAccountUseCase.accept(1L))
                .isInstanceOf(AccountIsNotClosed.class);
    }
}

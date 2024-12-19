package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CreditAccountUseCase useCase;

    @Test
    void creditAccountOf5By3ShouldGive8() {
        // Given
        var accountAfterOperationExpected = Account.builder().id(1L).amount(8.0f).build();
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));
        when(accountRepository.save(Mockito.any())).thenReturn(savedAccount);

        // When
        var accountAfterOperationActual = useCase.accept(1L, 3.0f);

        // Then
        assertThat(accountAfterOperationActual).isEqualTo(accountAfterOperationExpected);
    }

    @Test
    void creditAccountByNullValueShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> useCase.accept(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value cannot be null");
    }

    @Test
    void creditAccountByNegativeValueShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> useCase.accept(1L, -1f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be positive");
    }

    @Test
    void creditAccountOfNonExistingAccountShouldThrowException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.accept(1L, 3.0f))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

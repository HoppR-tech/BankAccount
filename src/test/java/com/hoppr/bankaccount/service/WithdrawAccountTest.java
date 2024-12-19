package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.NotEnoughMoney;
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
public class WithdrawAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private WithdrawUseCase useCase;

    @Test
    void moneyWithdrawOfAccount5By3ShouldBeOK() {
        // Given
        var accountAfterOperationExpected = Account.builder().id(1L).amount(2.0f).build();
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));
        when(accountRepository.save(Mockito.any())).thenReturn(savedAccount);

        // When
        var accountAfterOperationActual = useCase.withdrawMoney(1L, 3.0f);

        // Then
        assertThat(accountAfterOperationActual).isEqualTo(accountAfterOperationExpected);
    }

    @Test
    void moneyWithdrawOfAccount5By6ShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> useCase.withdrawMoney(1L, 6.0f))
                .isInstanceOf(NotEnoughMoney.class);
    }

    @Test
    void withdrawOfNegativeNumberShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> useCase.withdrawMoney(1L, -1f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be positive");
    }

    @Test
    void withdrawOfNullValueShouldThrowException() {
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        assertThatThrownBy(() -> useCase.withdrawMoney(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be positive");
    }

    @Test
    void withdrawOfNonExistingAccountShouldThrowException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.withdrawMoney(1L, 3.0f))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

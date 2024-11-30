package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.NotEnoughMoneyException;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
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
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Nested
    class Credit {
        @Test
        void creditAccountOf5By3ShouldGive8() {
            // Given
            var accountAfterOperationExpected = Account.builder().id(1L).amount(8.0f).build();
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));
            when(accountRepository.save(Mockito.any())).thenReturn(savedAccount);

            // When
            var accountAfterOperationActual = accountService.creditAccount(1L, 3.0f);

            // Then
            assertThat(accountAfterOperationActual).isEqualTo(accountAfterOperationExpected);
        }

        @Test
        void creditAccountByNullValueShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.creditAccount(1L, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void creditAccountByNegativeValueShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.creditAccount(1L, -1f))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void creditAccountOfNonExistingAccountShouldThrowException() {
            when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> accountService.creditAccount(1L, 3.0f))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Nested
    class Debit {
        @Test
        void debitAccountOf5By3ShouldGive2() {
            // Given
            var accountAfterOperationExpected = Account.builder().id(1L).amount(2.0f).build();
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));
            when(accountRepository.save(Mockito.any())).thenReturn(savedAccount);

            // When
            var accountAfterOperationActual = accountService.debitAccount(1L, 3.0f);

            // Then
            assertThat(accountAfterOperationActual).isEqualTo(accountAfterOperationExpected);
        }

        @Test
        void debitAccountByNegativeNumberShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.debitAccount(1L, -1f))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void debitAccountByNullValueShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.debitAccount(1L, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void debitAccountOfNonExistingAccountShouldThrowException() {
            when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> accountService.debitAccount(1L, 3.0f))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Nested
    class Withdraw {
        @Test
        void moneyWithdrawOfAccount5By3ShouldBeOK() {
            // Given
            var accountAfterOperationExpected = Account.builder().id(1L).amount(2.0f).build();
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));
            when(accountRepository.save(Mockito.any())).thenReturn(savedAccount);

            // When
            var accountAfterOperationActual = accountService.withdrawMoney(1L, 3.0f);

            // Then
            assertThat(accountAfterOperationActual).isEqualTo(accountAfterOperationExpected);
        }

        @Test
        void moneyWithdrawOfAccount5By6ShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.withdrawMoney(1L, 6.0f))
                    .isInstanceOf(NotEnoughMoneyException.class);

        }

        @Test
        void withdrawOfNegativeNumberShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.withdrawMoney(1L, -1f))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void withdrawOfNullValueShouldThrowException() {
            var savedAccount = Account.builder().id(1L).amount(5.0f).build();
            when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

            assertThatThrownBy(() -> accountService.withdrawMoney(1L, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Amount must be positive");
        }

        @Test
        void withdrawOfNonExistingAccountShouldThrowException() {
            when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> accountService.withdrawMoney(1L, 3.0f))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }
}

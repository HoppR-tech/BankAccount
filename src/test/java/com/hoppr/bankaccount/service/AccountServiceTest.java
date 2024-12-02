package com.hoppr.bankaccount.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountAlreadyBlockedException;
import com.hoppr.bankaccount.exception.AccountNotBlockedException;
import com.hoppr.bankaccount.exception.NotEnoughMoneyException;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock private AccountRepository accountRepository;

  @InjectMocks private AccountService accountService;

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

  @Nested
  class BlockAccount {
    @Test
    void unblockedAccountShouldBeBlocked() {
      // Given
      var argumentCaptor = ArgumentCaptor.forClass(Account.class);
      var savedAccount = Account.builder().id(1L).amount(5.0f).build();
      when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

      // When
      accountService.blockAccount(1L);
      verify(accountRepository).save(argumentCaptor.capture());

      // Then
      assertThat(argumentCaptor.getValue().isClosed()).isTrue();
    }

    @Test
    void blockAccountWhenAlreadyBlockedShouldThrowException() {
      var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
      when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

      assertThatThrownBy(() -> accountService.blockAccount(1L))
          .isInstanceOf(AccountAlreadyBlockedException.class);
    }

    @Test
    void blockedAccountShouldBeUnblocked() {
      // Given
      var argumentCaptor = ArgumentCaptor.forClass(Account.class);
      var savedAccount = Account.builder().id(1L).amount(5.0f).closed(true).build();
      when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

      // When
      accountService.unblockAccount(1L);
      verify(accountRepository).save(argumentCaptor.capture());

      // Then
      assertThat(argumentCaptor.getValue().isClosed()).isFalse();
    }

    @Test
    void unblockAccountWhenAlreadyUnblockedShouldThrowException() {
      var savedAccount = Account.builder().id(1L).amount(5.0f).closed(false).build();
      when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

      assertThatThrownBy(() -> accountService.unblockAccount(1L))
          .isInstanceOf(AccountNotBlockedException.class);
    }
  }
}

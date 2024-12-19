package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ConsultAccountUseCase useCase;

    @Test
    void existingAccountShouldReturnAccount() {
        // Given
        var savedAccount = Account.builder().id(1L).amount(5.0f).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(savedAccount));

        // When
        var accountActual = useCase.getAccount(1L);

        // Then
        assertThat(accountActual).isEqualTo(savedAccount);
    }

    @Test
    void nonExistingAccountShouldThrowException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.getAccount(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

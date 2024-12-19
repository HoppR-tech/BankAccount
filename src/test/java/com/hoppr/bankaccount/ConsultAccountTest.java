package com.hoppr.bankaccount;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import com.hoppr.bankaccount.domain.usecases.consult.ConsultAccountUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
        when(accountRepository.get(1L)).thenReturn(savedAccount);

        // When
        var accountActual = useCase.getAccount(1L);

        // Then
        assertThat(accountActual).isEqualTo(savedAccount);
    }

}

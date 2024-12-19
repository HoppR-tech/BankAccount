package com.hoppr.bankaccount.infra.repository;

import com.hoppr.bankaccount.domain.model.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JpaAccount {
    @Id
    private Long id;
    private Float amount;
    private boolean closed;

    public static JpaAccount from(Account account) {
        return JpaAccount.builder()
                .id(account.id())
                .amount(account.amount())
                .closed(account.closed())
                .build();
    }
}

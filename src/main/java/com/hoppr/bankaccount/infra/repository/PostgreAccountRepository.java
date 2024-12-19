package com.hoppr.bankaccount.infra.repository;

import com.hoppr.bankaccount.domain.model.Account;
import com.hoppr.bankaccount.domain.model.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostgreAccountRepository implements AccountRepository {

    private final JpaAccounts jpaAccounts;

    @Override
    public Account get(Long id) {
        return jpaAccounts.findById(id)
                .map(this::toAccount)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Account toAccount(JpaAccount jpaAccount) {
        return Account.builder()
                .id(jpaAccount.getId())
                .amount(jpaAccount.getAmount())
                .closed(jpaAccount.isClosed())
                .build();
    }

    @Override
    public Account save(Account account) {
        JpaAccount entity = JpaAccount.from(account);
        JpaAccount persisted = jpaAccounts.save(entity);
        return toAccount(persisted);
    }
}

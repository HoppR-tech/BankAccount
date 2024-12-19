package com.hoppr.bankaccount.repository;

import com.hoppr.bankaccount.entity.Account;
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
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Account save(Account account) {
        return jpaAccounts.save(account);
    }
}

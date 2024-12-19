package com.hoppr.bankaccount.repository;

import com.hoppr.bankaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccounts extends JpaRepository<Account, Long> {
}

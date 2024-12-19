package com.hoppr.bankaccount.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccounts extends JpaRepository<JpaAccount, Long> {
}

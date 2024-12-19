package com.hoppr.bankaccount.domain.model;

public interface AccountRepository {

    Account get(Long id);

    Account save(Account account);

}

package com.hoppr.bankaccount.repository;

import com.hoppr.bankaccount.entity.Account;

public interface AccountRepository {

    Account get(Long id);

    Account save(Account account);

}

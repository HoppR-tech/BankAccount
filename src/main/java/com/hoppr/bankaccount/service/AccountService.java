package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public Account creditAccount(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Credit amount must be positive");
    }

    account.setAmount(
        BigDecimal.valueOf(amount).add(BigDecimal.valueOf(account.getAmount())).floatValue());

    return accountRepository.save(account);
  }
}

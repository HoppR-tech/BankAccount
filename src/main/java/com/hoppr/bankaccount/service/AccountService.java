package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.NotEnoughMoneyException;
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

    checkValidAmount(amount);

    account.setAmount(
        BigDecimal.valueOf(amount).add(BigDecimal.valueOf(account.getAmount())).floatValue());

    return accountRepository.save(account);
  }

  public Account debitAccount(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    checkValidAmount(amount);

    account.setAmount(BigDecimal.valueOf(account.getAmount()).subtract(BigDecimal.valueOf(amount)).floatValue());

    return accountRepository.save(account);
  }

  public Account withdrawMoney(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    checkValidAmount(amount);

    if(amount > account.getAmount()){
      throw new NotEnoughMoneyException("Not enough money on the account");
    }

    return debitAccount(id, amount);
  }

  void checkValidAmount(Float amount){
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
  }
}

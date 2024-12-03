package com.hoppr.bankaccount.service;

import com.hoppr.bankaccount.entity.Account;
import com.hoppr.bankaccount.exception.AccountAlreadyClosedException;
import com.hoppr.bankaccount.exception.AccountClosedException;
import com.hoppr.bankaccount.exception.AccountNotClosedException;
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

  public Account getAccount(Long id) {
    return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public Account creditAccount(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkClosedAccount(account);
    checkValidAmount(amount);

    account.setAmount(
        BigDecimal.valueOf(amount).add(BigDecimal.valueOf(account.getAmount())).floatValue());

    return accountRepository.save(account);
  }

  public Account debitAccount(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkClosedAccount(account);
    checkValidAmount(amount);

    account.setAmount(
        BigDecimal.valueOf(account.getAmount()).subtract(BigDecimal.valueOf(amount)).floatValue());

    return accountRepository.save(account);
  }

  public Account withdrawMoney(Long id, Float amount) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkClosedAccount(account);
    checkValidAmount(amount);

    if (amount > account.getAmount()) {
      throw new NotEnoughMoneyException("Not enough money on the account");
    }

    return debitAccount(id, amount);
  }

  void checkValidAmount(Float amount) {
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
  }

  public void closeAccount(Long id) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    if (account.isClosed()) {
      throw new AccountAlreadyClosedException("Account already closed");
    }

    account.setClosed(true);
    accountRepository.save(account);
  }

  private void checkClosedAccount(Account account) {
    if (account.isClosed()) {
      throw new AccountClosedException("Cannot perform operation on closed account");
    }
  }

  public void reopenAccount(Long id) {
    var account = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    if (!account.isClosed()) {
      throw new AccountNotClosedException("Account is not closed");
    }

    account.setClosed(false);
    accountRepository.save(account);
  }
}

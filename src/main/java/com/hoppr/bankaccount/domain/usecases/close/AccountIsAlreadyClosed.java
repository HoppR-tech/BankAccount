package com.hoppr.bankaccount.domain.usecases.close;

public class AccountIsAlreadyClosed extends RuntimeException {
  public AccountIsAlreadyClosed(String message) {
    super(message);
  }
}

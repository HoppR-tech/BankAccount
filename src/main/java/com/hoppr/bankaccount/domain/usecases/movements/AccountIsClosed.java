package com.hoppr.bankaccount.domain.usecases.movements;

public class AccountIsClosed extends RuntimeException {
  public AccountIsClosed(String message) {
    super(message);
  }
}

package com.hoppr.bankaccount.entity;

import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.exception.AccountIsClosed;
import com.hoppr.bankaccount.exception.AccountIsNotClosed;
import com.hoppr.bankaccount.exception.NotEnoughMoney;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
@Getter
@EqualsAndHashCode
@ToString
public class Account {

  private Long id;
  private Float amount;
  private boolean closed;

  public void reOpen() {
    if (!closed) {
      throw new AccountIsNotClosed("Account is not closed");
    }
    closed = false;
  }

  public void close() {
    if (closed) {
      throw new AccountIsAlreadyClosed("Account already closed");
    }
    closed = true;
  }

  public void credit(Amount amount) {
    checkClosedAccount();
    amountMustBePositive(amount);

    this.amount = amount.add(Amount.of(this.amount)).value();
  }

  public void debit(Amount amount) {
    checkClosedAccount();
    amountMustBePositive(amount);

    this.amount = Amount.of(this.amount).subtract(amount).value();
  }

  public void withdraw(Amount amount) {
    checkClosedAccount();
    amountMustBePositive(amount);

    if (amount.greaterThan(Amount.of(this.amount))) {
      throw new NotEnoughMoney("Not enough money on the account");
    }

    this.amount = Amount.of(this.amount).subtract(amount).value();
  }

  private void checkClosedAccount() {
    if (closed) {
      throw new AccountIsClosed("Cannot perform operation on closed account");
    }
  }

  void amountMustBePositive(Amount amount) {
    if (!amount.isPositive()) {
      throw new IllegalArgumentException("Amount must be positive");
    }
  }

}

package com.hoppr.bankaccount.entity;

import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.exception.AccountIsClosed;
import com.hoppr.bankaccount.exception.AccountIsNotClosed;
import com.hoppr.bankaccount.exception.NotEnoughMoney;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id private Long id;

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

  public void credit(Float amount) {
    checkClosedAccount();
    checkValidAmount(amount);

    this.amount = BigDecimal.valueOf(amount)
            .add(BigDecimal.valueOf(this.amount)).floatValue();
  }

  public void debit(Float amount) {
    checkClosedAccount();
    checkValidAmount(amount);

    this.amount = BigDecimal.valueOf(this.getAmount())
            .subtract(BigDecimal.valueOf(amount)).floatValue();
  }

  public void withdraw(Float amount) {
    checkClosedAccount();
    checkValidAmount(amount);

    if (amount > this.amount) {
      throw new NotEnoughMoney("Not enough money on the account");
    }

    this.amount = BigDecimal.valueOf(this.getAmount())
            .subtract(BigDecimal.valueOf(amount)).floatValue();
  }

  private void checkClosedAccount() {
    if (closed) {
      throw new AccountIsClosed("Cannot perform operation on closed account");
    }
  }

  void checkValidAmount(Float amount) {
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
  }

}

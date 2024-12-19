package com.hoppr.bankaccount.controller.dto;

import com.hoppr.bankaccount.entity.Account;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccountDTO implements Serializable {
  private Long id;
  private Float amount;
  private boolean closed;

  public static AccountDTO fromEntity(Account account) {
    return AccountDTO.builder()
        .id(account.id())
        .amount(account.amount())
        .closed(account.closed())
        .build();
  }
}

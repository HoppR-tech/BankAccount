package com.hoppr.bankaccount.controller.dto;

import com.hoppr.bankaccount.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccountDTO {
  private Long id;
  private Float amount;
  private boolean closed;

  public static AccountDTO fromEntity(Account account) {
    return AccountDTO.builder()
        .id(account.getId())
        .amount(account.getAmount())
        .closed(account.isClosed())
        .build();
  }
}

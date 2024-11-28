package com.hoppr.bankaccount.controller;

import com.hoppr.bankaccount.controller.dto.AccountDTO;
import com.hoppr.bankaccount.controller.dto.AccountOperationDTO;
import com.hoppr.bankaccount.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

  public ResponseEntity<AccountDTO> creditAccount(AccountOperationDTO accountOperationDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.creditAccount(
                    accountOperationDTO.getId(), accountOperationDTO.getAmount())));
  }
}

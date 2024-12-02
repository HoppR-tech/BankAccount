package com.hoppr.bankaccount.controller;

import com.hoppr.bankaccount.controller.dto.AccountDTO;
import com.hoppr.bankaccount.controller.dto.OperationAmountDTO;
import com.hoppr.bankaccount.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping("/{accountId}/credit")
  public ResponseEntity<AccountDTO> creditAccount(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.creditAccount(accountId, operationAmountDTO.getAmount())));
  }

  @PostMapping("/{accountId}/debit")
  public ResponseEntity<AccountDTO> debitAccount(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.debitAccount(accountId, operationAmountDTO.getAmount())));
  }

  @PostMapping("/{accountId}/withdraw")
  public ResponseEntity<AccountDTO> withdraw(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.withdrawMoney(accountId, operationAmountDTO.getAmount())));
  }

  @PostMapping("/{accountId}/block")
  public ResponseEntity<AccountDTO> blockAccount(@PathVariable("accountId") Long accountId) {
    accountService.blockAccount(accountId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{accountId}/unblock")
  public ResponseEntity<AccountDTO> unblockAccount(@PathVariable("accountId") Long accountId) {
    accountService.unblockAccount(accountId);
    return ResponseEntity.ok().build();
  }
}

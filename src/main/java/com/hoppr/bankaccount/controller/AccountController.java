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

  @GetMapping("/{accountId}")
  public ResponseEntity<AccountDTO> getAccount(@PathVariable("accountId") Long accountId) {
    return ResponseEntity.ok().body(AccountDTO.fromEntity(accountService.getAccount(accountId)));
  }

  @PatchMapping("/{accountId}/credit")
  public ResponseEntity<AccountDTO> creditAccount(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.creditAccount(accountId, operationAmountDTO.getAmount())));
  }

  @PatchMapping("/{accountId}/debit")
  public ResponseEntity<AccountDTO> debitAccount(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.debitAccount(accountId, operationAmountDTO.getAmount())));
  }

  @PatchMapping("/{accountId}/withdraw")
  public ResponseEntity<AccountDTO> withdraw(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                accountService.withdrawMoney(accountId, operationAmountDTO.getAmount())));
  }

  @PatchMapping("/{accountId}/close")
  public ResponseEntity<AccountDTO> closeAccount(@PathVariable("accountId") Long accountId) {
    accountService.closeAccount(accountId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{accountId}/reopen")
  public ResponseEntity<AccountDTO> reopenAccount(@PathVariable("accountId") Long accountId) {
    accountService.reopenAccount(accountId);
    return ResponseEntity.ok().build();
  }
}

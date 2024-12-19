package com.hoppr.bankaccount.domain.usecases.movements.withdraw;

import com.hoppr.bankaccount.infra.controller.dto.AccountDTO;
import com.hoppr.bankaccount.infra.controller.dto.OperationAmountDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class WithdrawAccountRoute {

  private final WithdrawUseCase useCase;

  @PatchMapping(value = "/{accountId}/withdraw", consumes = "application/json")
  public ResponseEntity<AccountDTO> withdraw(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                useCase.accept(accountId, operationAmountDTO.getAmount())));
  }

}

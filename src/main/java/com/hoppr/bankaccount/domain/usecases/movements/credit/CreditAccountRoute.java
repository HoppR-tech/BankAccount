package com.hoppr.bankaccount.domain.usecases.movements.credit;

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
public class CreditAccountRoute {

  private final CreditAccountUseCase useCase;

  @PatchMapping(value = "/{accountId}/credit", consumes = "application/json")
  public ResponseEntity<AccountDTO> creditAccount(
      @PathVariable("accountId") Long accountId,
      @RequestBody OperationAmountDTO operationAmountDTO) {
    return ResponseEntity.ok()
        .body(
            AccountDTO.fromEntity(
                useCase.accept(accountId, operationAmountDTO.getAmount())));
  }

}

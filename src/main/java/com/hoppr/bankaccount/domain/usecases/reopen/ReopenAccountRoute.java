package com.hoppr.bankaccount.domain.usecases.reopen;

import com.hoppr.bankaccount.infra.controller.dto.AccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class ReopenAccountRoute {

  private final ReopenAccountUseCase useCase;

  @PatchMapping(value = "/{accountId}/reopen")
  public ResponseEntity<AccountDTO> reopenAccount(@PathVariable("accountId") Long accountId) {
    useCase.accept(accountId);
    return ResponseEntity.ok().build();
  }
}

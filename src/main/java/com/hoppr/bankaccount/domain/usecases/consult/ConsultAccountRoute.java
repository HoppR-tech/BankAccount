package com.hoppr.bankaccount.domain.usecases.consult;

import com.hoppr.bankaccount.infra.controller.dto.AccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class ConsultAccountRoute {

  private final ConsultAccountUseCase useCase;

  @GetMapping("/{accountId}")
  public ResponseEntity<AccountDTO> getAccount(@PathVariable("accountId") Long accountId) {
    return ResponseEntity.ok().body(AccountDTO.fromEntity(useCase.getAccount(accountId)));
  }

}

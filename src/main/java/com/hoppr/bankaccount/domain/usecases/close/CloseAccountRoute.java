package com.hoppr.bankaccount.domain.usecases.close;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class CloseAccountRoute {

  private final CloseAccountUseCase useCase;

  @PatchMapping(value = "/{accountId}/close")
  public void closeAccount(@PathVariable("accountId") Long accountId) {
    useCase.accept(accountId);
  }

}

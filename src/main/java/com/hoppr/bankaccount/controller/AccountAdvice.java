package com.hoppr.bankaccount.controller;

import com.hoppr.bankaccount.exception.AccountIsAlreadyClosed;
import com.hoppr.bankaccount.exception.AccountIsClosed;
import com.hoppr.bankaccount.exception.AccountIsNotClosed;
import com.hoppr.bankaccount.exception.NotEnoughMoney;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(
      value = {
        AccountIsAlreadyClosed.class,
        AccountIsNotClosed.class,
        NotEnoughMoney.class,
        IllegalArgumentException.class,
        AccountIsClosed.class
      })
  public ResponseEntity<BusinessError> handleBusinessErrors(RuntimeException ex) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new BusinessError(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<BusinessError> handleEntityNotFoundErrors(RuntimeException ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new BusinessError(HttpStatus.NOT_FOUND, "Account not found"));
  }
}

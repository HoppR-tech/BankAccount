package com.hoppr.bankaccount.domain.usecases.movements.withdraw;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney(String message) {
        super(message);
    }
}

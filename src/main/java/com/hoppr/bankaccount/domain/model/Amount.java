package com.hoppr.bankaccount.domain.model;

import java.math.BigDecimal;

public record Amount(Float value) {

    public Amount {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
    }

    public boolean isPositive() {
        return value > 0;
    }

    public Amount subtract(Amount other) {
        BigDecimal result = BigDecimal.valueOf(this.value).subtract(BigDecimal.valueOf(other.value));
        return new Amount(result.floatValue());
    }

    public Amount add(Amount other) {
        BigDecimal result = BigDecimal.valueOf(this.value).add(BigDecimal.valueOf(other.value));
        return new Amount(result.floatValue());
    }

    public boolean greaterThan(Amount other) {
        return this.value > other.value;
    }

    public static Amount of(Float value) {
        return new Amount(value);
    }
}

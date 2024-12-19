package com.hoppr.bankaccount.infra.controller;

import org.springframework.http.HttpStatus;

public record BusinessError(HttpStatus status, String message) {}

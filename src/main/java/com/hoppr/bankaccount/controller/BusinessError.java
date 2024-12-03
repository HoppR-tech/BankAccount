package com.hoppr.bankaccount.controller;

import org.springframework.http.HttpStatus;

public record BusinessError(HttpStatus status, String message) {}

package com.hoppr.bankaccount.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Account {

  private Long id;

  private Float amount;

  private boolean closed;
}

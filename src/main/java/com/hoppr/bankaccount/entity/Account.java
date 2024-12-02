package com.hoppr.bankaccount.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Account {

  @Id private Long id;

  private Float amount;

  private boolean closed;
}

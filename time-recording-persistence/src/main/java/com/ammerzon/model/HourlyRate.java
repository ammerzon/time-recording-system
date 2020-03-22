package com.ammerzon.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HourlyRate {
  @Id @GeneratedValue private Long id;

  @Enumerated(EnumType.STRING)
  private Position position;

  private LocalDateTime year;

  private double amount;
}

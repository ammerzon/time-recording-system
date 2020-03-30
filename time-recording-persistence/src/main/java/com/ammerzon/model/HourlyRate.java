package com.ammerzon.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class HourlyRate {
  @Id @GeneratedValue private Long id;

  @Column(name = "hourlyRateYear")
  private LocalDateTime year;

  private double amount;

  @ManyToOne private Position position;
}

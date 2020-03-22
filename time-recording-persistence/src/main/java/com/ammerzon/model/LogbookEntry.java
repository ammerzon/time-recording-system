package com.ammerzon.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LogbookEntry {
  @Id @GeneratedValue private Long id;

  private String activity;

  private LocalDateTime startTime;

  @ManyToOne private Employee employee;

  @ManyToOne private Project project;

  @Enumerated(EnumType.STRING)
  private CostType costType;

  public void attachEmployee(Employee employee) {
    if (this.employee != null) {
      this.employee.getLogbookEntries().remove(this);
    }

    if (employee != null) {
      employee.getLogbookEntries().add(this);
    }

    this.employee = employee;
  }
}

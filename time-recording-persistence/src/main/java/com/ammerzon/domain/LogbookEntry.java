package com.ammerzon.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
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

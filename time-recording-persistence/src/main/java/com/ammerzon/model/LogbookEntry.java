package com.ammerzon.model;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Data
@NoArgsConstructor
@Cacheable
public class LogbookEntry {
  @Id @GeneratedValue private Long id;

  private String activity;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private double duration;

  @Fetch(FetchMode.SELECT)
  @ManyToOne(fetch = FetchType.EAGER)
  private Employee employee;

  @ManyToOne private Project project;

  @Enumerated(EnumType.STRING)
  private CostType costType;

  @PrePersist
  private void calcDuration() {
    var diff = Duration.between(startTime, endTime);
    duration = diff.toHoursPart() + diff.toMinutesPart() / 60.0;
  }

  public void attachEmployee(Employee employee) {
    if (this.employee != null) {
      this.employee.getLogbookEntries().remove(this);
    }

    if (employee != null) {
      employee.getLogbookEntries().add(this);
    }

    this.employee = employee;
  }

  public void attachProject(Project project) {
    if (this.project != null) {
      this.project.getLogbookEntries().remove(this);
    }

    if (project != null) {
      project.getLogbookEntries().add(this);
    }

    this.project = project;
  }
}

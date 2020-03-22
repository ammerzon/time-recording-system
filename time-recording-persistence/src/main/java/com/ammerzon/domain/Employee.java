package com.ammerzon.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"logbookEntries"})
public class Employee {
  @Id @GeneratedValue private Long id;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Position position;

  @OneToOne private Address address;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getEmployee() != null) {
      entry.getEmployee().getLogbookEntries().remove(entry);
    }

    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }
}

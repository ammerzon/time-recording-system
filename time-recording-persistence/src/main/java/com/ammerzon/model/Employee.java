package com.ammerzon.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"logbookEntries"})
@ToString(exclude = {"logbookEntries"})
public class Employee {
  @Id @GeneratedValue private Long id;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Position position;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @Fetch(FetchMode.SELECT)
  @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getEmployee() != null) {
      entry.getEmployee().getLogbookEntries().remove(entry);
    }

    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }
}

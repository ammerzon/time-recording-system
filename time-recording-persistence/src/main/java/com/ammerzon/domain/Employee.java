package com.ammerzon.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"projects", "logbookEntries"})
public class Employee {
  @Id @GeneratedValue private Long id;

  private String firstName;

  private String lastName;

  private LocalDate dateOfBirth;

  @OneToOne private Address address;

  @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Project> projects;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getEmployee() != null) {
      entry.getEmployee().getLogbookEntries().remove(entry);
    }

    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }

  public void addProject(Project project) {
    project.getMembers().add(this);
    this.projects.add(project);
  }
}

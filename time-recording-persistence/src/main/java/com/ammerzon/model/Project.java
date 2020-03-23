package com.ammerzon.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Project {
  @Id @GeneratedValue private Long id;

  private String name;

  @ManyToOne private Customer customer;

  @OneToMany(mappedBy = "project", cascade = CascadeType.MERGE)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getProject() != null) {
      entry.getProject().getLogbookEntries().remove(entry);
    }

    this.logbookEntries.add(entry);
    entry.setProject(this);
  }
}

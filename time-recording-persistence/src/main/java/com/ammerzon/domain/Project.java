package com.ammerzon.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Project {
  @Id @GeneratedValue private Long id;

  private String name;

  @ManyToMany private Set<Employee> members = new HashSet<>();

  public void addMember(Employee employee) {
    employee.getProjects().add(this);
    this.members.add(employee);
  }
}

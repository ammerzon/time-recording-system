package com.ammerzon.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Customer {
  @Id @GeneratedValue private long id;

  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Address address;

  private String name;

  public Customer(String name) {
    this.name = name;
  }
}

package com.ammerzon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Customer {
  @Id @GeneratedValue private long id;

  @OneToOne private Address address;

  private String name;
}

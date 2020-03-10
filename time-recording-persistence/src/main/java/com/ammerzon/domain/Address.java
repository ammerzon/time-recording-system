package com.ammerzon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {
  @Id @GeneratedValue private long id;

  private String zipCode;

  private String city;

  private String street;
}

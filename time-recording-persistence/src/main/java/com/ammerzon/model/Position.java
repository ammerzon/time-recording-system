package com.ammerzon.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hourlyRates"})
@ToString(exclude = {"hourlyRates"})
public class Position {
  @Id @GeneratedValue private Long id;

  @OneToMany(mappedBy = "position", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Set<HourlyRate> hourlyRates = new HashSet<>();

  @Column(unique = true)
  private String description;

  public void addHourlyRate(HourlyRate hourlyRate) {
    if (hourlyRate.getPosition() != null) {
      hourlyRate.getPosition().getHourlyRates().remove(hourlyRate);
    }

    this.hourlyRates.add(hourlyRate);
    hourlyRate.setPosition(this);
  }
}

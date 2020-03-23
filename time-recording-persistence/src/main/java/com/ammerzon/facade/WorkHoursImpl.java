package com.ammerzon.facade;

import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import javax.persistence.EntityManager;

public class WorkHoursImpl implements WorkHoursFacade {

  private EntityManager entityManager;

  public WorkHoursImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Map<CostType, Double> getWorkHoursByCostType(Customer customer, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Position, Double> getWorkHoursByPosition(Customer customer, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<CostType, Double> getWorkHoursByCostType(Project project, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<CostType, Double> getWorkHoursByCostType(Project project) {
    return null;
  }

  @Override
  public Map<Position, Double> getWorkHoursByPosition(Project project, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Project, Double> getWorkHoursByProject(Employee employee) {
    return null;
  }

  @Override
  public Map<ChronoUnit, Double> getWorkHours(Employee employee) {
    return null;
  }
}

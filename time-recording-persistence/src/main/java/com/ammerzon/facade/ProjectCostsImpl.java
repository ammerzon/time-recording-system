package com.ammerzon.facade;

import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.EntityManager;

public class ProjectCostsImpl implements ProjectCostsFacade {

  private EntityManager entityManager;

  public ProjectCostsImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Map<CostType, Double> getCostsByCostType(Customer customer, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Position, Double> getCostsByPosition(Customer customer, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<CostType, Double> getCostsByCostType(Project project, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Position, Double> getCostsByPosition(Project project, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Employee, Double> getCostsByEmployee(Project project, LocalDateTime from,
      LocalDateTime to) {
    return null;
  }
}

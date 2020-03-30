package com.ammerzon.logic;

import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Position;
import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.EntityManager;

public class ProjectCostsImpl implements ProjectCostsFacade {

  private EntityManager entityManager;

  public ProjectCostsImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Map<CostType, Double> getCostsByCostType(
      Customer customer, LocalDateTime from, LocalDateTime to) {
    return null;
  }

  @Override
  public Map<Long, Double> getCostsByPosition(
      Customer customer, LocalDateTime from, LocalDateTime to) {
    return null;
  }
}

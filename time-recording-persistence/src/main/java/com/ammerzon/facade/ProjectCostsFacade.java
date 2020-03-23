package com.ammerzon.facade;

import com.ammerzon.interceptor.Transactional;
import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import java.time.LocalDateTime;
import java.util.Map;

public interface ProjectCostsFacade {
  @Transactional
  Map<CostType, Double> getCostsByCostType(Customer customer, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Position, Double> getCostsByPosition(Customer customer, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<CostType, Double> getCostsByCostType(Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Position, Double> getCostsByPosition(Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Employee, Double> getCostsByEmployee(Project project, LocalDateTime from, LocalDateTime to);
}

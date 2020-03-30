package com.ammerzon.logic;

import com.ammerzon.interceptor.Transactional;
import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public interface WorkHoursFacade {
  @Transactional
  Map<CostType, Double> getWorkHoursByCostType(
      Customer customer, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Position, Double> getWorkHoursByPosition(
      Customer customer, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<CostType, Double> getWorkHoursByCostType(
      Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<CostType, Double> getWorkHoursByCostType(Project project);

  @Transactional
  Map<Position, Double> getWorkHoursByPosition(
      Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Project, Double> getWorkHoursByProject(Employee employee);

  @Transactional
  Map<ChronoUnit, Double> getWorkHours(Employee employee);
}

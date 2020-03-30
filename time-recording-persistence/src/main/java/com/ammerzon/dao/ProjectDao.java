package com.ammerzon.dao;

import com.ammerzon.interceptor.Transactional;
import com.ammerzon.model.CostType;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import com.ammerzon.repository.Repository;
import java.time.LocalDateTime;
import java.util.Map;

public interface ProjectDao extends Repository<Project, Long> {
  @Transactional
  Map<CostType, Double> getCostsByCostType(Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Long, Double> getCostsByPosition(Project project, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Long, Double> getCostsByEmployee(Project project, LocalDateTime from, LocalDateTime to);
}

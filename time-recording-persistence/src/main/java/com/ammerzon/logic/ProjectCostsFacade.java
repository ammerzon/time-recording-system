package com.ammerzon.logic;

import com.ammerzon.interceptor.Transactional;
import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import java.time.LocalDateTime;
import java.util.Map;

public interface ProjectCostsFacade {
  @Transactional
  Map<CostType, Double> getCostsByCostType(Customer customer, LocalDateTime from, LocalDateTime to);

  @Transactional
  Map<Long, Double> getCostsByPosition(Customer customer, LocalDateTime from, LocalDateTime to);
}

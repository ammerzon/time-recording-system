package com.ammerzon.dao;

import com.ammerzon.model.Employee;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class EmployeeDaoImpl extends DbRepository<Employee, Long> implements EmployeeDao {

  public EmployeeDaoImpl(EntityManager entityManager) {
    super(Employee.class, entityManager);
  }
}

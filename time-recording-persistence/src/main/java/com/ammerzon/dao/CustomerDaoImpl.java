package com.ammerzon.dao;

import com.ammerzon.model.Customer;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class CustomerDaoImpl extends DbRepository<Customer, Long> implements CustomerDao {

  public CustomerDaoImpl(EntityManager entityManager) {
    super(Customer.class, entityManager);
  }
}

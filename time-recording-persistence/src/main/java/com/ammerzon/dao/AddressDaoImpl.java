package com.ammerzon.dao;

import com.ammerzon.model.Address;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class AddressDaoImpl extends DbRepository<Address, Long> implements AddressDao {

  public AddressDaoImpl(EntityManager entityManager) {
    super(Address.class, entityManager);
  }
}

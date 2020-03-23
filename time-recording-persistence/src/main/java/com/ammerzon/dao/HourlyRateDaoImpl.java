package com.ammerzon.dao;

import com.ammerzon.model.HourlyRate;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class HourlyRateDaoImpl extends DbRepository<HourlyRate, Long> implements HourlyRateDao {

  public HourlyRateDaoImpl(EntityManager entityManager) {
    super(HourlyRate.class, entityManager);
  }
}

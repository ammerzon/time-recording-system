package com.ammerzon.dao;

import com.ammerzon.model.Position;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class PositionDaoImpl extends DbRepository<Position, Long> implements PositionDao {

  public PositionDaoImpl(EntityManager entityManager) {
    super(Position.class, entityManager);
  }
}

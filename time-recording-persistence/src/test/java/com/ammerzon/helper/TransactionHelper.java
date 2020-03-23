package com.ammerzon.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionHelper {
  private EntityManager entityManager;

  public TransactionHelper(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void transactional(Runnable runnable) {
    EntityTransaction entityTransaction = entityManager.getTransaction();

    try {
      entityTransaction.begin();
      runnable.run();
      entityTransaction.commit();
    } catch (Exception ex) {
      if (entityTransaction.isActive()) {
        entityTransaction.rollback();
      }
    }
  }
}

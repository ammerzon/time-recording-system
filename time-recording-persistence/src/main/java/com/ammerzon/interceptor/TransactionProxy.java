package com.ammerzon.interceptor;

import com.ammerzon.helper.EntityManagerStore;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionProxy implements InvocationHandler {

  private Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

  private EntityManagerStore entityManagerStore;
  private Object invocationTarget;

  public TransactionProxy(Object invocationTarget, EntityManagerStore entityManagerStore) {
    this.invocationTarget = invocationTarget;
    this.entityManagerStore = entityManagerStore;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = null;
    EntityManager entityManager = entityManagerStore.get();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    if (!method.isAnnotationPresent(Transactional.class))
      return method.invoke(invocationTarget, args);

    try {
      System.out.printf("--> starting transaction (%s)%n", method.getName());
      entityTransaction.begin();
      result = method.invoke(invocationTarget, args);
      entityTransaction.commit();
      System.out.printf("<-- ending transaction (%s)%n", result.getClass().getName());
    } catch (Exception ex) {
      try {
        if (entityTransaction.isActive()) {
          entityTransaction.rollback();
          logger.debug("Rolled back transaction");
        }
      } catch (Exception e1) {
        logger.warn("Rollback of transaction failed: " + e1);
      }
    }

    return result;
  }
}

package com.ammerzon.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerStoreImpl implements EntityManagerStore {

  private final Logger logger = LoggerFactory.getLogger(EntityManagerStoreImpl.class);
  private EntityManagerFactory entityManagerFactory;
  private ThreadLocal<EntityManager> entityManagerThread = new ThreadLocal<>();

  public EntityManagerStoreImpl() {
    entityManagerFactory = Persistence.createEntityManagerFactory("TimeRecordingPU");
  }

  @Override
  public EntityManager get() {
    logger.debug("Getting the current entity manager");
    if (entityManagerThread.get() == null) {
      logger.warn("No entity manager was found");
      return null;
    }

    return entityManagerThread.get();
  }

  @Override
  public EntityManager createAndRegister() {
    logger.debug("Creating new entity manager");
    final var entityManager = entityManagerFactory.createEntityManager();
    entityManagerThread.set(entityManager);
    return entityManager;
  }

  @Override
  public void unregister(EntityManager entityManager) {
    logger.debug("Unregistering the entity manager");
    if (entityManagerThread.get() != null) entityManagerThread.get().close();
    entityManagerThread.set(null);
  }
}

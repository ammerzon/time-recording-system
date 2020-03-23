package com.ammerzon.helper;

import javax.persistence.EntityManager;

public interface EntityManagerStore {
  /**
   * Looks for the current entity manager and returns it. The method returns null if no entity
   * manager is found.
   *
   * @return the currently used entity manager or {@code null} if none was found.
   */
  EntityManager get();

  /**
   * Creates an entity manager and stores it in a stack.
   *
   * @return the new entity manager.
   */
  EntityManager createAndRegister();

  /**
   * Removes an entity manager from the thread local stack.
   *
   * @param entityManager - the entity manager to remove.
   * @throws IllegalStateException in case the entity manager was not found on the stack.
   */
  void unregister(EntityManager entityManager);
}

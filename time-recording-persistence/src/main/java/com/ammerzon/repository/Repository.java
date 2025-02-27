package com.ammerzon.repository;

import com.ammerzon.interceptor.Transactional;
import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities.
   */
  @Transactional
  long count();

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation
   * might have changed the entity instance completely.
   *
   * @param entity - must not be null.
   * @return the saved entity; will never be null.
   */
  @Transactional
  T save(T entity);

  /**
   * Saves all given entities.
   *
   * @param entities - must not be null nor must it contain null.
   * @return the saved entities; will never be null. The returned Iterable will have the same size
   *     as the Iterable passed as an argument.
   */
  @Transactional
  Iterable<T> saveAll(Iterable<T> entities);

  /**
   * Deletes the entity with the given id.
   *
   * @param id - must not be null.
   */
  @Transactional
  void deleteById(ID id);

  /**
   * Deletes a given entity.
   *
   * @param entity - must not be null.
   */
  @Transactional
  void delete(T entity);

  /**
   * Retrieves an entity by its id.
   *
   * @param id - must not be null.
   * @return The entity with the given id or Optional#empty() if none found.
   */
  @Transactional
  Optional<T> findById(ID id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Transactional
  List<T> findAll();
}

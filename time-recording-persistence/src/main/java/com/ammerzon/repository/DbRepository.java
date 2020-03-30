package com.ammerzon.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.Session;

public abstract class DbRepository<T, ID> implements Repository<T, ID> {

  protected EntityManager entityManager;
  private Class<T> clazz;

  public DbRepository(Class<T> clazz, EntityManager entityManager) {
    this.clazz = clazz;
    this.entityManager = entityManager;
  }

  @Override
  public long count() {
    return entityManager
        .createQuery("select count(c) from " + clazz.getName() + " c", Long.class)
        .getSingleResult();
  }

  @Override
  public T save(T entity) {
    Session session = entityManager.unwrap(Session.class);
    session.saveOrUpdate(entity);
    return entity;
  }

  @Override
  public Iterable<T> saveAll(Iterable<T> entities) {
    List<T> savedEntities = new ArrayList<>();
    entities.forEach(t -> savedEntities.add(save(t)));
    return savedEntities;
  }

  @Override
  public void deleteById(ID id) {
    Optional<T> entity = findById(id);
    entity.ifPresent(this::delete);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  @Override
  public Optional<T> findById(ID id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  @Override
  public List<T> findAll() {
    return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
  }
}

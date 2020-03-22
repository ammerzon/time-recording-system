package com.ammerzon.repository;

import java.util.List;
import java.util.Optional;

public class DbRepository<T, ID> implements Repository<T, ID> {

  @Override
  public long count() {
    return 0;
  }

  @Override
  public T save(T entity) {
    return null;
  }

  @Override
  public Iterable<T> saveAll(Iterable<T> entities) {
    return null;
  }

  @Override
  public void deleteById(ID id) {

  }

  @Override
  public void delete(T entity) {

  }

  @Override
  public Optional<T> findById(ID id) {
    return Optional.empty();
  }

  @Override
  public List<T> findAll() {
    return null;
  }
}

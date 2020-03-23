package com.ammerzon.dao;

import com.ammerzon.model.Project;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class ProjectDaoImpl extends DbRepository<Project, Long> implements ProjectDao {

  public ProjectDaoImpl(EntityManager entityManager) {
    super(Project.class, entityManager);
  }
}

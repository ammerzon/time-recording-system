package com.ammerzon.dao;

import com.ammerzon.model.LogbookEntry;
import com.ammerzon.repository.DbRepository;
import javax.persistence.EntityManager;

public class LogbookEntryDaoImpl extends DbRepository<LogbookEntry, Long>
    implements LogbookEntryDao {

  public LogbookEntryDaoImpl(EntityManager entityManager) {
    super(LogbookEntry.class, entityManager);
  }
}

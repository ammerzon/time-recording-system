package com.ammerzon.dao;

import com.ammerzon.model.CostType;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Employee_;
import com.ammerzon.model.HourlyRate;
import com.ammerzon.model.HourlyRate_;
import com.ammerzon.model.LogbookEntry_;
import com.ammerzon.model.Position;
import com.ammerzon.model.Position_;
import com.ammerzon.model.Project;
import com.ammerzon.model.Project_;
import com.ammerzon.repository.DbRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

public class ProjectDaoImpl extends DbRepository<Project, Long> implements ProjectDao {

  public ProjectDaoImpl(EntityManager entityManager) {
    super(Project.class, entityManager);
  }

  @Override
  public Map<CostType, Double> getCostsByCostType(
      Project project, LocalDateTime from, LocalDateTime to) {
    var builder = entityManager.getCriteriaBuilder();
    var query = builder.createTupleQuery();
    var rootProject = query.from(Project.class);
    var logbookEntryJoin = rootProject.join(Project_.logbookEntries, JoinType.INNER);
    var employeeJoin = logbookEntryJoin.join(LogbookEntry_.employee, JoinType.INNER);
    var positionJoin = employeeJoin.join(Employee_.position, JoinType.INNER);
    var hourlyRateJoin = positionJoin.join(Position_.hourlyRates);

    builder.greaterThan(logbookEntryJoin.get(LogbookEntry_.startTime), from);
    builder.lessThan(logbookEntryJoin.get(LogbookEntry_.endTime), to);
    builder.equal(rootProject.get(Project_.id), project.getId());

    query.groupBy(logbookEntryJoin.get(LogbookEntry_.costType),employeeJoin.get(Employee_.id), hourlyRateJoin.get(HourlyRate_.amount));

    query.multiselect(
        logbookEntryJoin.get(LogbookEntry_.costType),
        employeeJoin.get(Employee_.id),
        builder.prod(
            hourlyRateJoin.get(HourlyRate_.amount),
            builder.sum(logbookEntryJoin.get(LogbookEntry_.duration))));

    query.orderBy(builder.asc(logbookEntryJoin.get(LogbookEntry_.costType)));

    var map = new HashMap<CostType, Double>();
    entityManager
        .createQuery(query)
        .getResultList()
        .forEach(
            tuple -> {
              var costType = (CostType) tuple.get(0);
              if (map.containsKey(costType)) {
                map.put(costType, map.get(costType) + (Double) tuple.get(2));
              } else {
                map.put((CostType) tuple.get(0), (Double) tuple.get(2));
              }
            });
    return map;
  }

  @Override
  public Map<Long, Double> getCostsByPosition(
      Project project, LocalDateTime from, LocalDateTime to) {
    var builder = entityManager.getCriteriaBuilder();
    var query = builder.createTupleQuery();
    var rootProject = query.from(Project.class);
    var logbookEntryJoin = rootProject.join(Project_.logbookEntries, JoinType.INNER);
    var employeeJoin = logbookEntryJoin.join(LogbookEntry_.employee, JoinType.INNER);
    var positionJoin = employeeJoin.join(Employee_.position, JoinType.INNER);
    var hourlyRateJoin = positionJoin.join(Position_.hourlyRates);

    builder.greaterThan(logbookEntryJoin.get(LogbookEntry_.startTime), from);
    builder.lessThan(logbookEntryJoin.get(LogbookEntry_.endTime), to);
    builder.equal(rootProject.get(Project_.id), project.getId());

    query.groupBy(positionJoin.get(Position_.id),employeeJoin.get(Employee_.id), hourlyRateJoin.get(HourlyRate_.amount));

    query.multiselect(
        positionJoin.get(Position_.id),
        builder.prod(
            hourlyRateJoin.get(HourlyRate_.amount),
            builder.sum(logbookEntryJoin.get(LogbookEntry_.duration))));

    query.orderBy(builder.asc(positionJoin.get(Position_.id)));

    var map = new HashMap<Long, Double>();
    entityManager
        .createQuery(query)
        .getResultList()
        .forEach(
            tuple -> {
              var positionId = (Long) tuple.get(0);
              if (map.containsKey(positionId)) {
                map.put(positionId, map.get(positionId) + (Double) tuple.get(1));
              } else {
                map.put((Long) tuple.get(0), (Double) tuple.get(1));
              }
            });

    return map;
  }

  @Override
  public Map<Long, Double> getCostsByEmployee(
      Project project, LocalDateTime from, LocalDateTime to) {
    var builder = entityManager.getCriteriaBuilder();
    var query = builder.createTupleQuery();
    var rootProject = query.from(Project.class);
    var logbookEntryJoin = rootProject.join(Project_.logbookEntries, JoinType.INNER);
    var employeeJoin = logbookEntryJoin.join(LogbookEntry_.employee, JoinType.INNER);
    var positionJoin = employeeJoin.join(Employee_.position, JoinType.INNER);
    var hourlyRateJoin = positionJoin.join(Position_.hourlyRates);

    builder.greaterThan(logbookEntryJoin.get(LogbookEntry_.startTime), from);
    builder.lessThan(logbookEntryJoin.get(LogbookEntry_.endTime), to);
    builder.equal(rootProject.get(Project_.id), project.getId());

    query.groupBy(employeeJoin.get(Employee_.id), hourlyRateJoin.get(HourlyRate_.amount));

    query.multiselect(
        employeeJoin.get(Employee_.id),
        builder.prod(
            hourlyRateJoin.get(HourlyRate_.amount),
            builder.sum(logbookEntryJoin.get(LogbookEntry_.duration))));

    query.orderBy(builder.asc(employeeJoin.get(Employee_.id)));

    var map = new HashMap<Long, Double>();
    entityManager
        .createQuery(query)
        .getResultList()
        .forEach(
            tuple -> {
              var employeeId = (Long) tuple.get(0);
              if (map.containsKey(employeeId)) {
                map.put(employeeId, map.get(employeeId) + (Double) tuple.get(1));
              } else {
                map.put((Long) tuple.get(0), (Double) tuple.get(1));
              }
            });
    return map;
  }
}

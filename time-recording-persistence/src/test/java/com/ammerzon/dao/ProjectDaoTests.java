package com.ammerzon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ammerzon.helper.EntityManagerStore;
import com.ammerzon.helper.EntityManagerStoreImpl;
import com.ammerzon.helper.TransactionHelper;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.LogbookEntry;
import com.ammerzon.model.Project;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectDaoTests {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private EntityManagerStore entityManagerStore;
  private TransactionHelper helper;
  private EntityManager entityManager;
  private ProjectDao projectDao;
  private EmployeeDao employeeDao;
  private LogbookEntryDao logbookEntryDao;

  @BeforeEach
  public void beforeEach() {
    entityManagerStore = new EntityManagerStoreImpl();
    entityManager = entityManagerStore.createAndRegister();
    helper = new TransactionHelper(entityManager);
    projectDao = new ProjectDaoImpl(entityManager);
    employeeDao = new EmployeeDaoImpl(entityManager);
    logbookEntryDao = new LogbookEntryDaoImpl(entityManager);
  }

  @AfterEach
  public void afterEach() {
    entityManager = entityManagerStore.get();
    if (entityManager != null) {
      entityManagerStore.unregister(entityManager);
    }
  }

  @Test
  public void whenNoProjects_ThenCountIsNull() {
    assertEquals(0, projectDao.count());
  }

  @Test
  public void whenInsertingProject_ThenCountReturnsOne() {
    var project = new Project();
    project.setName("Project1");
    helper.transactional(() -> projectDao.save(project));
    assertEquals(1, projectDao.count());
  }

  @Test
  public void whenInsertingMultipleProjects_ThenCountReturnsTheAmount() {
    var project1 = new Project();
    var project2 = new Project();
    var project3 = new Project();
    List<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);
    projects.add(project3);

    helper.transactional(() -> projectDao.saveAll(projects));

    assertEquals(3, projectDao.count());
  }

  @Test
  public void whenInsertingProject_ThenFindReturnsIt() {
    var name = "Project1";
    var projectId = new AtomicLong();

    helper.transactional(
        () -> {
          var project = new Project();
          project.setName(name);
          projectId.set(projectDao.save(project).getId());
        });

    helper.transactional(
        () -> {
          var project = projectDao.findById(projectId.get());
          assertTrue(project.isPresent());
          assertEquals(projectId.get(), project.get().getId());
          assertEquals(name, project.get().getName());
        });
  }

  @Test
  public void whenSavingOneProject_ThenItShouldUpdateTheValues() {
    var project = new Project();
    var employee = new Employee();
    employee.setFirstName("Max");
    employee.setLastName("Mustermann");
    var logbookEntry = new LogbookEntry();
    logbookEntry.setEmployee(employee);
    logbookEntry.setStartTime(LocalDateTime.now());
    logbookEntry.setEndTime(LocalDateTime.now());
    project.setCustomer(new Customer("Customer1"));
    project.addLogbookEntry(logbookEntry);
    project.setName("Project1");
    var dbProject = new AtomicReference<Project>();

    helper.transactional(
        () -> {
          dbProject.set(projectDao.save(project));
          assertEquals(project.getName(), dbProject.get().getName());
        });

    helper.transactional(
        () -> {
          dbProject.get().setName("Project2");
          projectDao.save(dbProject.get());
        });

    helper.transactional(
        () -> {
          assertEquals(1, projectDao.count());
          var updatedProject = projectDao.findById(dbProject.get().getId());
          assertTrue(updatedProject.isPresent());
          assertEquals(updatedProject.get().getName(), dbProject.get().getName());
        });
  }

  @Test
  public void whenInsertingProjectAndDeletingIt_ThenCountIsZero() {
    var project = new Project();
    project.setName("Project1");
    var projectId = new AtomicLong();

    helper.transactional(() -> projectId.set(projectDao.save(project).getId()));

    helper.transactional(
        () -> {
          projectDao.deleteById(projectId.get());
          assertEquals(0, projectDao.count());
        });
  }
}

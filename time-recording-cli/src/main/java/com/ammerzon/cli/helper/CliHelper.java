package com.ammerzon.cli.helper;

import com.ammerzon.cli.Entity;
import com.ammerzon.dao.AddressDaoImpl;
import com.ammerzon.dao.CustomerDaoImpl;
import com.ammerzon.dao.EmployeeDaoImpl;
import com.ammerzon.dao.HourlyRateDaoImpl;
import com.ammerzon.dao.LogbookEntryDaoImpl;
import com.ammerzon.dao.PositionDaoImpl;
import com.ammerzon.dao.ProjectDao;
import com.ammerzon.dao.ProjectDaoImpl;
import com.ammerzon.helper.EntityManagerStore;
import com.ammerzon.helper.EntityManagerStoreImpl;
import com.ammerzon.interceptor.TransactionProxy;
import com.ammerzon.model.Address;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.HourlyRate;
import com.ammerzon.model.LogbookEntry;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import com.ammerzon.repository.Repository;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import org.reflections.ReflectionUtils;

public class CliHelper {
  public static EntityManagerStore entityManagerStore = new EntityManagerStoreImpl();
  public static EntityManager entityManager = entityManagerStore.createAndRegister();
  private static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static DateTimeFormatter localDateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static Repository getRepositoryProxy(Entity entityType) {
    Repository repository = new CustomerDaoImpl(entityManager);
    switch (entityType) {
      case Customer:
        repository = new CustomerDaoImpl(entityManager);
        break;
      case Employee:
        repository = new EmployeeDaoImpl(entityManager);
        break;
      case HourlyRate:
        repository = new HourlyRateDaoImpl(entityManager);
        break;
      case LogbookEntry:
        repository = new LogbookEntryDaoImpl(entityManager);
        break;
      case Project:
        repository = new ProjectDaoImpl(entityManager);
        break;
      case Address:
        repository = new AddressDaoImpl(entityManager);
        break;
      case Position:
        repository = new PositionDaoImpl(entityManager);
        break;
    }
    return (Repository)
        Proxy.newProxyInstance(
            Repository.class.getClassLoader(),
            new Class[] {Repository.class},
            new TransactionProxy(repository, entityManagerStore));
  }

  public static ProjectDao getProjectDaoProxy() {
    var repository = new ProjectDaoImpl(entityManager);

    return (ProjectDao)
        Proxy.newProxyInstance(
            ProjectDao.class.getClassLoader(),
            new Class[] {ProjectDao.class},
            new TransactionProxy(repository, entityManagerStore));
  }

  @SuppressWarnings("unchecked")
  public static Class getClass(Entity entityType) {
    switch (entityType) {
      case Customer:
        return Customer.class;
      case Employee:
        return Employee.class;
      case HourlyRate:
        return HourlyRate.class;
      case LogbookEntry:
        return LogbookEntry.class;
      case Project:
        return Project.class;
      case Address:
        return Address.class;
      case Position:
        return Position.class;
    }

    return null;
  }

  public static Entity getEntityType(Class<?> clazz) {
    if (clazz == Customer.class) {
      return Entity.Customer;
    } else if (clazz == Employee.class) {
      return Entity.Employee;
    } else if (clazz == HourlyRate.class) {
      return Entity.HourlyRate;
    } else if (clazz == LogbookEntry.class) {
      return Entity.LogbookEntry;
    } else if (clazz == Project.class) {
      return Entity.Project;
    } else if (clazz == Address.class) {
      return Entity.Address;
    } else if (clazz == Position.class) {
      return Entity.Position;
    }

    return null;
  }

  /**
   * Fill the fields of an entity by asking the user for input. ToDo: Needs refactoring into
   * multiple methods ToDo: Needs better exception handling when invalid user input. ToDo: Support
   * collections
   *
   * @param entity The entity to read
   * @param clazz The class of the entity
   * @return The filled entity
   */
  @SuppressWarnings("unchecked")
  public static Object readValuesForEntity(Object entity, Class clazz) {
    var in = new Scanner(System.in);

    if (entity == null) {
      try {
        var constructor = clazz.getConstructor();
        entity = constructor.newInstance();
      } catch (Exception ignored) {
      }
    }

    var fields = ReflectionUtils.getAllFields(clazz);
    for (var field : fields) {
      try {
        field.setAccessible(true);
        var value = field.get(entity);

        var shouldUpdate = false;
        if (value != null
            && !Collection.class.isAssignableFrom(field.getType())
            && !field.isAnnotationPresent(Id.class)) {
          System.out.print(
              "Update value of "
                  + field.getName().replaceAll("com.ammerzon.model.", "")
                  + "? Current value: "
                  + value
                  + " (y/n) > ");
          shouldUpdate = in.nextLine().equals("y");
        }

        if ((value == null || shouldUpdate || Collection.class.isAssignableFrom(field.getType()))
            && !field.isAnnotationPresent(Id.class)) {
          if (field.getClass().isPrimitive() || field.getType() == String.class) {
            // Handle primitives and strings
            System.out.print(
                clazz.getName().replaceAll("com.ammerzon.model.", "")
                    + ": "
                    + field.getName().replaceAll("com.ammerzon.model.", "")
                    + " > ");
            field.set(entity, field.getType().cast(in.nextLine()));
          } else if (field.getType() == LocalDate.class) {
            // Handle LocalDates
            System.out.print(
                clazz.getName().replaceAll("com.ammerzon.model.", "")
                    + ": "
                    + field.getName().replaceAll("com.ammerzon.model.", "")
                    + " (yyyy-MM-dd) > ");
            LocalDate date = LocalDate.parse(in.nextLine(), localDateFormatter);
            field.set(entity, date);
          } else if (field.getType() == LocalDateTime.class) {
            // Handle LocalDateTimes
            System.out.print(
                clazz.getName().replaceAll("com.ammerzon.model.", "")
                    + ": "
                    + field.getName().replaceAll("com.ammerzon.model.", "")
                    + " (yyyy-MM-dd HH:mm:ss) > ");
            LocalDateTime dateTime = LocalDateTime.parse(in.nextLine(), localDateTimeFormatter);
            field.set(entity, dateTime);
          } else if (field.getType().isEnum()) {
            // Handle enums
            System.out.print(
                field.getName().replaceAll("com.ammerzon.model.", "")
                    + " ("
                    + Arrays.toString(field.getType().getEnumConstants())
                    + ") > ");

            var enumValue = Enum.valueOf((Class<Enum>) field.getType(), in.nextLine().toUpperCase());
            field.set(entity, enumValue);
          } else if (!Collection.class.isAssignableFrom(field.getType())) {
            // Handle objects
            var repository = CliHelper.getRepositoryProxy(CliHelper.getEntityType(field.getType()));

            if (repository.count() > 0) {
              var chooseExisting = false;
              System.out.print(
                  "Choose existing "
                      + field.getName().replaceAll("com.ammerzon.model.", "")
                      + " (y/n)? > ");
              chooseExisting = in.nextLine().equals("y");

              if (chooseExisting) {
                repository.findAll().forEach(System.out::println);
                System.out.print(
                    "Enter id of " + field.getName().replaceAll("com.ammerzon.model.", "") + " > ");
                var id = Long.parseLong(in.nextLine());
                var loadedEntity = repository.findById(id).get();
                field.set(entity, loadedEntity);
              } else {
                field.set(entity, readValuesForEntity(value, field.getType()));
              }
            } else {
              field.set(entity, readValuesForEntity(value, field.getType()));
            }
          }
        }

      } catch (Exception ignored) {
        ignored.printStackTrace();
      }
    }

    return entity;
  }
}

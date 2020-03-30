package com.ammerzon.cli.command;

import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.model.CostType;
import com.ammerzon.model.Customer;
import com.ammerzon.model.Employee;
import com.ammerzon.model.HourlyRate;
import com.ammerzon.model.LogbookEntry;
import com.ammerzon.model.Position;
import com.ammerzon.model.Project;
import com.ammerzon.repository.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "itd",
    aliases = "insertTestData",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to insert test data",
    subcommands = CommandLine.HelpCommand.class)
public class InsertTestDataCommand implements Runnable {
  @ParentCommand CliCommands parent;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @SuppressWarnings("unchecked")
  public void run() {
    Repository<Project, Long> proxy = CliHelper.getRepositoryProxy(Entity.Project);
    proxy.save(generateTestProject());
    System.out.println("Inserted test data");
  }

  private Project generateTestProject() {
    Repository<Employee, Long> employeeDaoProxy = CliHelper.getRepositoryProxy(Entity.Employee);
    Repository<LogbookEntry, Long> logbookDaoProxy =
        CliHelper.getRepositoryProxy(Entity.LogbookEntry);
    var customer = new Customer();
    customer.setName("Customer1");

    var hourlyRate1 = new HourlyRate();
    hourlyRate1.setYear(LocalDateTime.now());
    hourlyRate1.setAmount(12);
    var hourlyRate2 = new HourlyRate();
    hourlyRate2.setYear(LocalDateTime.now());
    hourlyRate2.setAmount(15);

    var position1 = new Position();
    position1.setDescription("Position1");
    position1.addHourlyRate(hourlyRate1);

    var position2 = new Position();
    position2.setDescription("Position2");
    position2.addHourlyRate(hourlyRate2);

    var employee1 = new Employee();
    employee1.setFirstName("FirstName1");
    employee1.setLastName("LastName1");
    employee1.setPosition(position1);
    employeeDaoProxy.save(employee1);

    var employee2 = new Employee();
    employee2.setFirstName("FirstName2");
    employee2.setLastName("LastName2");
    employee2.setPosition(position2);
    employeeDaoProxy.save(employee2);

    var logbookEntry1 = new LogbookEntry();
    logbookEntry1.setStartTime(LocalDateTime.parse("2020-01-01 08:00:00", formatter));
    logbookEntry1.setEndTime(LocalDateTime.parse("2020-01-01 10:00:00", formatter));
    logbookEntry1.setCostType(CostType.DEVELOPMENT);
    logbookEntry1.attachEmployee(employee1);
    logbookEntry1.setActivity("Activity1");
    logbookDaoProxy.save(logbookEntry1);

    var logbookEntry2 = new LogbookEntry();
    logbookEntry2.setStartTime(LocalDateTime.parse("2020-01-01 10:00:00", formatter));
    logbookEntry2.setEndTime(LocalDateTime.parse("2020-01-01 12:00:00", formatter));
    logbookEntry2.setCostType(CostType.MAINTENANCE);
    logbookEntry2.attachEmployee(employee1);
    logbookEntry2.setActivity("Activity2");
    logbookDaoProxy.save(logbookEntry2);

    var logbookEntry3 = new LogbookEntry();
    logbookEntry3.setStartTime(LocalDateTime.parse("2020-01-01 08:00:00", formatter));
    logbookEntry3.setEndTime(LocalDateTime.parse("2020-01-01 10:00:00", formatter));
    logbookEntry3.setCostType(CostType.DEVELOPMENT);
    logbookEntry3.attachEmployee(employee2);
    logbookEntry3.setActivity("Activity3");
    logbookDaoProxy.save(logbookEntry3);

    var project = new Project();
    project.setCustomer(customer);
    project.setName("Project1");
    project.addLogbookEntry(logbookEntry1);
    project.addLogbookEntry(logbookEntry2);
    project.addLogbookEntry(logbookEntry3);
    return project;
  }
}

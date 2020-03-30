package com.ammerzon.cli.command;

import com.ammerzon.cli.CostGrouping;
import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.dao.ProjectDao;
import com.ammerzon.model.Employee;
import com.ammerzon.model.Position;
import com.ammerzon.repository.Repository;
import java.time.LocalDateTime;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "gc",
    aliases = "getCosts",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to calculate the costs",
    subcommands = CommandLine.HelpCommand.class)
public class GetCostsCommand implements Runnable {
  @ParentCommand CliCommands parent;

  @Option(
      required = true,
      names = {"-t", "--type"},
      description = {"Specify the type of grouping.", "Valid values: ${COMPLETION-CANDIDATES}"})
  private CostGrouping grouping;

  @Option(
      names = {"-i", "--id"},
      required = true,
      interactive = true)
  private Long id;

  @SuppressWarnings("unchecked")
  public void run() {
    ProjectDao proxy = CliHelper.getProjectDaoProxy();
    Repository positionDao = CliHelper.getRepositoryProxy(Entity.Position);
    Repository employeeDao = CliHelper.getRepositoryProxy(Entity.Employee);
    var project = proxy.findById(id);

    if (project.isPresent()) {
      switch (grouping) {
        case COST_TYPE:
          var costsByCostType =
              proxy.getCostsByCostType(project.get(), LocalDateTime.MIN, LocalDateTime.MAX);
          System.out.println("Costs grouped by cost type: ");
          costsByCostType.forEach((costType, cost) -> System.out
              .printf("%s: %s€%n", costType.name(), cost));
          break;
        case POSITION:
          var costsByPosition =
              proxy.getCostsByPosition(project.get(), LocalDateTime.MIN, LocalDateTime.MAX);
          System.out.println("Costs grouped by position: ");
          costsByPosition.forEach((id, cost) -> {
            var position = (Position) positionDao.findById(id).get();
            System.out
                .printf("%s: %s€%n", position.getDescription(), cost);
          });
          break;
        case EMPLOYEE:
          var costsByEmployee =
              proxy.getCostsByEmployee(project.get(), LocalDateTime.MIN, LocalDateTime.MAX);
          System.out.println("Costs grouped by employee: ");
          costsByEmployee.forEach((id, cost) -> {
            var employee = (Employee) employeeDao.findById(id).get();
            System.out
                .printf("%s %s: %s€%n", employee.getFirstName(), employee.getLastName(), cost);
          });
          break;
      }
    } else {
      System.out.printf("No entity of type %s found with id: %d%n", Entity.Project, id);
    }
  }
}

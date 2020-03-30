package com.ammerzon.cli.command;

import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.repository.Repository;
import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "ga",
    aliases = "getAll",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to get all entities of specific type",
    subcommands = CommandLine.HelpCommand.class)
public class GetAllCommand implements Runnable {
  @ParentCommand CliCommands parent;

  @Option(
      required = true,
      names = {"-t", "--type"},
      description = {"Specify the type of the entity.", "Valid values: ${COMPLETION-CANDIDATES}"})
  private Entity entityType;

  @SuppressWarnings("unchecked")
  public void run() {
    long startTime = System.currentTimeMillis();
    Repository proxy = CliHelper.getRepositoryProxy(entityType);
    List entities = proxy.findAll();
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("Elapsed time: " + elapsedTime);
    if (entities.size() == 0) {
      System.out.println("No entities found!");
    } else {
      entities.forEach(System.out::println);
    }
  }
}

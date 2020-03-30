package com.ammerzon.cli.command;

import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.repository.Repository;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "cnt",
    aliases = "count",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to count the entities of a specific type",
    subcommands = CommandLine.HelpCommand.class)
public class CountCommand implements Runnable {
  @ParentCommand CliCommands parent;

  @Option(
      required = true,
      names = {"-t", "--type"},
      description = {"Specify the type of the entity.", "Valid values: ${COMPLETION-CANDIDATES}"})
  private Entity entityType;

  @SuppressWarnings("unchecked")
  public void run() {
    Repository proxy = CliHelper.getRepositoryProxy(entityType);
    long amount = proxy.count();
    System.out.printf("Found %d entities of type %s%n", amount, entityType);
  }
}

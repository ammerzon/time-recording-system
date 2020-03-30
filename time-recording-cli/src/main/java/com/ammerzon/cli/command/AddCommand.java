package com.ammerzon.cli.command;

import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.repository.Repository;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "add",
    aliases = "insert",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to add an entity of a specific type",
    subcommands = CommandLine.HelpCommand.class)
public class AddCommand implements Runnable {
  @ParentCommand CliCommands parent;

  @Option(
      required = true,
      names = {"-t", "--type"},
      description = {"Specify the type of the entity.", "Valid values: ${COMPLETION-CANDIDATES}"})
  private Entity entityType;

  @SuppressWarnings("unchecked")
  public void run() {
    Repository proxy = CliHelper.getRepositoryProxy(entityType);
    Object entity = CliHelper.readValuesForEntity(null, CliHelper.getClass(entityType));
    Object savedEntity = proxy.save(entity);
    System.out.printf("Entity %s saved successfully!%n", savedEntity);
  }
}

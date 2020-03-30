package com.ammerzon.cli.command;

import com.ammerzon.cli.Entity;
import com.ammerzon.cli.helper.CliHelper;
import com.ammerzon.repository.Repository;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "upd",
    aliases = "update",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to update an entity of a specific type",
    subcommands = CommandLine.HelpCommand.class)
public class UpdateCommand implements Runnable {
  @ParentCommand CliCommands parent;

  @Option(
      required = true,
      names = {"-t", "--type"},
      description = {"Specify the type of the entity.", "Valid values: ${COMPLETION-CANDIDATES}"})
  private Entity entityType;

  @Option(
      names = {"-i", "--id"},
      required = true,
      interactive = true)
  private Long id;

  @SuppressWarnings("unchecked")
  public void run() {
    Repository proxy = CliHelper.getRepositoryProxy(entityType);
    Optional<Object> entity = proxy.findById(id);
    if (entity.isPresent()) {
      Object updatedEntity =
          CliHelper.readValuesForEntity(entity.get(), CliHelper.getClass(entityType));
      Object savedEntity = proxy.save(updatedEntity);
      System.out.printf("Entity %s updated successfully!%n", savedEntity);
    } else {
      System.out.printf("No entity of type %s found with id: %d%n", entityType, id);
    }
  }
}

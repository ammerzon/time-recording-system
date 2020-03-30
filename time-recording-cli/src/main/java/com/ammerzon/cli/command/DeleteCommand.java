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
    name = "del",
    aliases = "delete",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command to delete an entity of a specific type",
    subcommands = CommandLine.HelpCommand.class)
public class DeleteCommand implements Runnable {
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
      proxy.deleteById(id);
      System.out.printf("Entity %s deleted successfully!%n", entity);
    } else {
      System.out.printf("No entity of type %s found with id: %d%n", entityType, id);
    }
  }
}

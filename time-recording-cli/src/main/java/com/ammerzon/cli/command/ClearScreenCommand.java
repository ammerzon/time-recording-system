package com.ammerzon.cli.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "cls",
    aliases = "clear",
    mixinStandardHelpOptions = true,
    description = "Clears the screen",
    version = "1.0")
public class ClearScreenCommand implements Callable<Void> {
  @ParentCommand CliCommands parent;

  public Void call() {
    parent.reader.clearScreen();
    parent.reader.flush();
    return null;
  }
}

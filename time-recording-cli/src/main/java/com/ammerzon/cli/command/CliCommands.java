package com.ammerzon.cli.command;

import java.io.PrintWriter;
import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "",
    description = {"Hit @|magenta <TAB>|@ to see available commands.", ""},
    footer = {"", "Press Ctl-D to exit."},
    subcommands = {
      AddCommand.class,
      ClearScreenCommand.class,
      CountCommand.class,
      DeleteCommand.class,
      FindByIdCommand.class,
      GetAllCommand.class,
      UpdateCommand.class,
      InsertTestDataCommand.class,
      GetCostsCommand.class,
      CommandLine.HelpCommand.class
    })
public class CliCommands implements Runnable {
  LineReaderImpl reader;
  PrintWriter out;

  public CliCommands() {}

  public void setReader(LineReader reader) {
    this.reader = (LineReaderImpl) reader;
    out = reader.getTerminal().writer();
  }

  public void run() {
    System.out.println(new CommandLine(this).getUsageMessage());
  }
}

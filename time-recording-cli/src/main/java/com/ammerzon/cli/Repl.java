package com.ammerzon.cli;

import com.ammerzon.cli.command.CliCommands;
import com.ammerzon.cli.helper.CliHelper;
import java.nio.file.Paths;
import java.util.Arrays;
import org.fusesource.jansi.AnsiConsole;
import org.jline.builtins.Builtins;
import org.jline.builtins.CommandRegistry.CommandSession;
import org.jline.builtins.Options.HelpException;
import org.jline.builtins.Widgets.AutosuggestionWidgets;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

public class Repl {
  public static void main(String[] args) {
    System.out.println(
        " _____ _                 ______                       _ _             \n"
            + "|_   _(_)                | ___ \\                     | (_)            \n"
            + "  | |  _ _ __ ___   ___  | |_/ /___  ___ ___  _ __ __| |_ _ __   __ _ \n"
            + "  | | | | '_ ` _ \\ / _ \\ |    // _ \\/ __/ _ \\| '__/ _` | | '_ \\ / _` |\n"
            + "  | | | | | | | | |  __/ | |\\ \\  __/ (_| (_) | | | (_| | | | | | (_| |\n"
            + "  \\_/ |_|_| |_| |_|\\___| \\_| \\_\\___|\\___\\___/|_|  \\__,_|_|_| |_|\\__, |\n"
            + "                                                                 __/ |\n"
            + "                                                                |___/ ");

    AnsiConsole.systemInstall();
    try {
      // Set up JLine commands
      var workDir = Paths.get("");
      var builtins = new Builtins(workDir, null, null);
      builtins.rename(org.jline.builtins.Builtins.Command.TTOP, "top");
      builtins.alias("zle", "widget");
      builtins.alias("bindkey", "keymap");

      var systemCompleter = builtins.compileCompleters();
      var parser = new DefaultParser();
      var terminal = TerminalBuilder.builder().build();
      var reader =
          LineReaderBuilder.builder()
              .terminal(terminal)
              .completer(systemCompleter)
              .parser(parser)
              .variable(LineReader.INDENTATION, 2)
              .variable(LineReader.LIST_MAX, 100)
              .build();
      var autosuggestionWidgets = new AutosuggestionWidgets(reader);
      autosuggestionWidgets.enable();
      builtins.setLineReader(reader);

      // Set up Picocli commands
      var commands = new CliCommands();
      var cmd = new CommandLine(commands);
      var picocliCommands = new PicocliCommands(workDir, cmd);
      systemCompleter.add(picocliCommands.compileCompleters());
      systemCompleter.compile();
      commands.setReader(reader);

      // REPL loop
      var em = CliHelper.entityManager;
      while (true) {
        try {
          var line = reader.readLine("> ");
          if (line.matches("^\\s*#.*")) {
            continue;
          }
          ParsedLine parsedLine = reader.getParser().parse(line, 0);
          String[] arguments = parsedLine.words().toArray(new String[0]);
          String command = parser.getCommand(parsedLine.word());
          if (builtins.hasCommand(command)) {
            CommandSession session =
                new CommandSession(terminal, terminal.input(), System.out, System.err);
            builtins.execute(session, command, Arrays.copyOfRange(arguments, 1, arguments.length));
          } else {
            new CommandLine(commands).execute(arguments);
          }
        } catch (HelpException e) {
          HelpException.highlight(e.getMessage(), HelpException.defaultStyle()).print(terminal);
        } catch (UserInterruptException ignored) {
        } catch (EndOfFileException e) {
          return;
        } catch (Exception e) {
          AttributedStringBuilder asb = new AttributedStringBuilder();
          asb.append(e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
          asb.toAttributedString().println(terminal);
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}

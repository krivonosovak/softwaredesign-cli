package ru.spbau.mit.command;

import ru.spbau.mit.execute.Scope;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ExternalProcess is a special meta-command that tries to execute an external program by its name
 */
public class ExternalProcess extends Command {

    public ExternalProcess(List<String> arguments) {
        super(arguments);
    }

    /**
     * When executed this command tries to to execute an external program by its name (passed as the first argument)
     * Other arguments are interpreted as arguments for this external program. Its output is then returned as an output
     * of this command.
     * @param scope
     * @param inStream
     * @return
     * @throws Exception
     */
    @Override
    public String execute(Scope scope, String inStream)throws Exception {
        ProcessBuilder probuilder = new ProcessBuilder(arguments);
        Process process = probuilder.start();

        OutputStream os = process.getOutputStream();
        PrintStream printStream = new PrintStream(os);
        printStream.print(inStream);
        printStream.close();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br.lines().collect(Collectors.joining("\n"));
    }
}

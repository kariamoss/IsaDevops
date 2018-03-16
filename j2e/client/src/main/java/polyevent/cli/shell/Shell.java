package polyevent.cli.shell;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Inspired by
 * https://github.com/polytechnice-si/4A_ISA_TheCookieFactory/blob/develop/client/src/main/java/cli/framework/Shell.java
 */
public class Shell<T> {
    public final void run() {
        System.out.println("Client started.");
        System.out.println("Available command: createEvent\n");
        run(System.in, false, 0);
    }

    public void run(InputStream stream, boolean shouldEcho, int indent) {
        Scanner scanner = new Scanner(stream);
        boolean shouldContinue = true;

        while(shouldContinue) {
            System.out.flush();
            for(int i = 0; i < indent; i++) { System.out.print(" "); }
            System.out.print("> ");

            if(!scanner.hasNext()) { System.out.println("Reaching end of file"); break; }

            String keyword = scanner.next().trim();

            String rawArgs;
            List<String> args;

            if (scanner.hasNextLine()) {
                rawArgs = scanner.nextLine();
                args = Arrays.asList(rawArgs.split(" "))
                        .stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
            } else { rawArgs = ""; args = new LinkedList<>(); }

            if(shouldEcho) {
                System.out.println(keyword + " " + rawArgs);
            }
            try {
                if (keyword.startsWith("#") || keyword.equals(""))
                    shouldContinue = true;
                //else
                //    shouldContinue = processCommand(keyword, args);
            }
            catch (IllegalArgumentException iae) {
                System.err.println("Illegal arguments for command "+keyword+": " + args);
            } catch (Exception e) {
                System.err.println("Exception caught while processing command:\n  " + e);
            }
        }
    }
}

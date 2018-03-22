package polyevent.cli.shell;

import polyevent.cli.command.AbstractCommand;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Inspired by
 * https://github.com/polytechnice-si/4A_ISA_TheCookieFactory/blob/develop/client/src/main/java/cli/framework/Shell.java
 */
public class Shell<T> {

    // The commands available in this very Shell
    private Map<String, Class<? extends AbstractCommand<T>>> availableCommands;

    public Shell() {
        this.availableCommands = new HashMap<>();
    }

    public final void run() {
        System.out.println("Client started.");
        System.out.println("Available commands: " + availableCommands.values() + "t\n");
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
                args = Arrays.stream(rawArgs.split(" ")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
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

    /**
     * Registers an array of commands available in this Shell.
     * The implementation is inspired from the TCF project sample by
     * <a href="https://github.com/mosser">Sebastien Mosser</a>
     *
     * @param commands the array of commands that will be available for use
     *                 in this very {@link Shell}
     */
    @SafeVarargs
    protected final void register(Class<? extends AbstractCommand<T>>... commands) {
        for (Class<? extends AbstractCommand<T>> c : commands) {
            registerCommand(c);
        }
    }

    /**
     * Registers a command that this Shell supports,
     * by storing them in the {@link Map<String, AbstractCommand>} {@link #availableCommands}
     *
     * The implementation is inspired from the TCF project sample by
     * <a href="https://github.com/mosser">Sebastien Mosser</a>
     *
     * @param command the command to register and that will be available for the user
     *                interacting with this {@link Shell}
     */
    private void registerCommand(Class<? extends AbstractCommand<T>> command) {
        try {
            String name = command.newInstance().toString();
            if (name.contains(" "))
                throw new IllegalArgumentException("Unknown command : " + name);



        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}

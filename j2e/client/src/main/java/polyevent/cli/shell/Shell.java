package polyevent.cli.shell;

import polyevent.cli.command.AbstractCommand;

import javax.xml.ws.WebServiceException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Inspired by
 * https://github.com/polytechnice-si/4A_ISA_TheCookieFactory/blob/develop/client/src/main/java/cli/framework/Shell.java
 */
public class Shell<T> {

    public T api;


    // The commands available in this very Shell
    private Map<String, Class<? extends AbstractCommand<T>>> availableCommands;
    // Help symbols, corresponding to the client input that would trigger the help() method
    private final String[] HELP_COMMAND_SYMBOLS = {"?", "help"};

    public Shell() {
        this.availableCommands = new HashMap<>();
    }

    public final void run() {
        System.out.println("Client started.");
        System.out.println("Type ? or help to display the available commands\n");
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
            if (isHelpCommand(keyword)) {
                help();
            }
            else {
                try {
                    if (keyword.startsWith("#") || keyword.equals(""))
                        shouldContinue = true;
                    else
                        shouldContinue = processCommand(keyword, args);
                } catch (IllegalArgumentException iae) {
                    System.err.println("Illegal arguments for command " + keyword + ": " + args);
                } catch (WebServiceException wse) {
                    System.err.println("WebServiceException while processing command: " + wse);
                } catch (Exception e) {
                    System.err.println("Exception caught while processing command:\n  " + e.getMessage());
                }
            }
        }
    }

    /**
     * Returns true if the given input corresponds to one of the
     * {@link #HELP_COMMAND_SYMBOLS} symbols
     * @param keyword the client input, parsed from the command line
     * @return true if the client asked for the help command
     */
    private boolean isHelpCommand(String keyword) {
        for (String s : HELP_COMMAND_SYMBOLS) {
            if (s.equals(keyword))
                return true;
        }
        return false;
    }

    /**
     * Loops through the {@link #availableCommands} collection,
     * and displays the available commands, with their description
     *
     * The implementation is inspired from the TCF project sample by
     * <a href="https://github.com/mosser">Sebastien Mosser</a>
     */
    private void help() {
        List<Class<? extends AbstractCommand>> avail = new ArrayList<>(commands());
        avail.sort(Comparator.comparing(Class::getCanonicalName));
        for(Class<? extends AbstractCommand> c:  avail) {
            try {
                AbstractCommand instance = c.newInstance();
                System.out.println("- " + instance.command()+": " + instance.helper());
                // line break for prettier display
                System.out.println();
            }
            catch(InstantiationException|IllegalAccessException e) {
                System.err.println("Unable to print help for registered command " + c);
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a {@link Collection<Class<AbstractCommand<T>>>}
     * @return a collection of all the available commands in this {@link Shell}
     */
    private Collection<Class<? extends AbstractCommand<T>>> commands() {
        return availableCommands.values();
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
            String name = command.newInstance().command();
            if (name.contains(" "))
                throw new IllegalArgumentException("Unknown command : " + name);
            else
                availableCommands.put(name, command);

        } catch (IllegalAccessException | InstantiationException e) {
            System.err.println("Couldn't instantiate command " + command.toString());
        }
    }

    /**
     * Executes the command that has the given identifier, with the given arguments args
     * If the given command doesn't exist, this method should throw a
     * {@link IllegalArgumentException}
     *
     * The implementation is inspired from the TCF project sample by
     * <a href="https://github.com/mosser">Sebastien Mosser</a>
     *
     * @param keyWord the identifier of the command to process
     * @param args the arguments of the command (if any)
     */
    @SuppressWarnings("unchecked")
    private boolean processCommand(String keyWord, List<String> args) throws Exception {
        if (availableCommands.containsKey(keyWord)) {
            Class<? extends AbstractCommand<T>> c = availableCommands.get(keyWord);
            try {
                AbstractCommand commandInstance = c.newInstance();
                commandInstance.withShell(this);
                return commandInstance.process(args);

            } catch (InstantiationException | IllegalAccessException e) {
                System.err.println("Couldn't instantiate command : " + c.toString());
                return true;
            }
        }
        else {
            System.err.println("Command " + keyWord + " is not a command of the system.");
            return true;
        }
    }
}

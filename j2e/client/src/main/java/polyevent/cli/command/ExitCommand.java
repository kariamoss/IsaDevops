package polyevent.cli.command;

import api.EventApi;

import java.util.List;

public class ExitCommand extends AbstractCommand<EventApi> {
    /**
     * The identifier of the command
     *
     * @return the identifier of the command as a string
     */
    @Override
    public String command() {
        return "exit";
    }

    /**
     * Executes and processes the concrete
     * implementation of an {@link AbstractCommand}
     *
     * @throws Exception if the command fails or doesn't exist
     */
    @Override
    public void execute() throws Exception {
        // nothing to execute since an exit command has been fired
    }

    /**
     * Loads the arguments for the given command,
     * and instantiates business objects that are necessary
     * to perform the user functionality
     *
     * @param args the arguments of the command
     */
    @Override
    public void load(List<String> args) {
        // empty method as this command has no arguments
    }

    /**
     * The method to call when the user wants to know the usage of this command
     *
     * @return the usage of this command, with a little description of the command
     */
    @Override
    public String helper() {
        return "Exits the Shell and leaves the PolyEvent software\nUsage: " + command();
    }

    /**
     * Whether the should stay alive or not
     *
     * @return true if the shell should be kept alive
     */
    @Override
    public boolean shouldContinue() {
        // exit command fired, ofc we shouldn't continue
        return false;
    }
}

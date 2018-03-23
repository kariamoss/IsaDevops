package polyevent.cli.command;

import polyevent.cli.shell.Shell;

import java.util.List;

public abstract class AbstractCommand<T> {

    /**
     * The identifier of the command
     * @return the identifier of the command as a string
     */
    public abstract String command();

    /**
     * Executes and processes the concrete
     * implementation of an {@link AbstractCommand}
     * @throws Exception if the command fails or doesn't exist
     */
    public abstract void execute() throws Exception;

    /**
     * Loads the arguments for the given command,
     * and instantiates business objects that are necessary
     * to perform the user functionality
     * @param args the arguments of the command
     */
    public abstract void load(List<String> args);

    /**
     * The method to call when the user wants to know the usage of this command
     * @return the usage of this command, with a little description of the command
     */
    public abstract String helper();

    /**
     * Whether the should stay alive or not
     * @return true if the shell should be kept alive
     */
    public boolean shouldContinue() { return true; }  // default implementation

    protected Shell<T> shell;

    public void withShell(Shell<T> shell) { this.shell = shell;   }

    public boolean process(List<String> args) throws Exception {
        try { load(args); }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        execute();

        // whether the shell should stay alive or not
        return shouldContinue();
    }
}
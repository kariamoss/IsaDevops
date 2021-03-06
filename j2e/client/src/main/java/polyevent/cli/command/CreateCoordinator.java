package polyevent.cli.command;

import api.EventApi;
import polyevent.dto.CoordinatorDTO;

import java.util.List;

/**
 * The class that will represent the command
 * to create a coordinator.
 * Needed args for the coordinator creation are :
 *
 * <li>
 *     <ul>The coordinator first name</ul>
 *     <ul>The coordinator last name</ul>
 *     <ul>The coordinator email</ul>
 *     <ul>A password</ul>
 * </li>
 */

public class CreateCoordinator extends AbstractCommand<EventApi>{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public String command() {
        return "createCoordinator";
    }

    @Override
    public void execute() throws Exception {
        AuthentificationCommand.cache = shell.api.coordinatorService.register(firstName, lastName, email, password);

        // request was successful
        if (AuthentificationCommand.cache != null)
            System.out.println(new CoordinatorDTO(AuthentificationCommand.cache).toString());
        else
            System.err.println("Failed to create new coordinator");
    }

    @Override
    public String helper() {
        return "Creates a new coordinator account.\nUsage: " + command() + " firstName lastName email password ";
    }


    @Override
    public void load(List<String> args) {
        firstName = args.get(0);
        lastName = args.get(1);
        email = args.get(2);
        password = args.get(3);
    }
}

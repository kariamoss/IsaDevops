package polyevent;

import api.EventApi;
import polyevent.cli.command.*;
import polyevent.cli.shell.Shell;

/**
 * The main entry point of the Client shell
 * This class overrides {@link Shell<EventApi>} in order
 * to register the available commands, and start the {@link Shell}
 * "main loop".
 *
 */
public class Client extends Shell<EventApi> {

    public Client() {


        api = new EventApi();
        // Add new a command classes here
        register(
                // to create a new event
                CreateEventCommand.class,

                // to perform an event retrieval
                GetEventsCommand.class,
                GetEventWithNameCommand.class,

                // to create a new coordinator
                CreateCoordinator.class,

                // to authenticate a coordinator
                AuthentificationCommand.class,

                // to exit the shell
                ExitCommand.class,
                AuthentificationCommand.class
        );
    }

    public static void main(String[] args )
    {
        System.out.println("Starting PolyEvent software\n");
        System.out.println();

        Client client = new Client();
        client.run();
    }
}

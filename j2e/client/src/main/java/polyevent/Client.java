package polyevent;

import api.EventApi;
import polyevent.cli.command.CreateEventCommand;
import polyevent.cli.command.ExitCommand;
import polyevent.cli.command.GetEventWithNameCommand;
import polyevent.cli.command.GetEventsCommand;
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
        // Add new command classes here
        register(
                // to create a new event
                CreateEventCommand.class,

                // to perform an event retrieval
                GetEventsCommand.class,
                GetEventWithNameCommand.class,

                // to exit the shell
                ExitCoand.class
        );
    }

    public static void main(String[] args )
    {
        System.out.println("Starting PolyEvent software\n");
        System.out.println("Your new event organizer helper!\n");
        System.out.println();

        Client client = new Client();
        client.run();
    }
}

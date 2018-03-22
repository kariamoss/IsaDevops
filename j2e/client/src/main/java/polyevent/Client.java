package polyevent;

import api.EventApi;
import polyevent.cli.command.CreateEventCommand;
import polyevent.cli.command.ExitCommand;
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

        // Add new command classes here
        register(
                CreateEventCommand.class,
                ExitCommand.class
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

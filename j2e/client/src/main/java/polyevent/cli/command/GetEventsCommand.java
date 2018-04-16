package polyevent.cli.command;

import api.EventApi;
import polyevent.entities.Event;

import java.util.List;

public class GetEventsCommand extends AbstractCommand<EventApi> {
    /**
     * The identifier of the command
     *
     * @return the identifier of the command as a string
     */
    @Override
    public String command() {
        return "getEvents";
    }

    /**
     * Executes and processes the concrete
     * implementation of an {@link AbstractCommand}
     *
     * @throws Exception if the command fails or doesn't exist
     */
    @Override
    public void execute() throws Exception {
        List<Event> events = shell.api.eventCatalogService.getAllEvents();
        System.out.println("Events created with PolyEvent : ");
        for (Event e : events)
            System.out.println("\t" + e.toString());
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
        return "Retrieves all the events that were created with PolyEvent\nUsage: " + command();
    }
}

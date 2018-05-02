package polyevent.cli.command;

import api.EventApi;
import polyevent.Event;
import polyevent.dto.EventDTO;

import java.util.List;

public class GetEventWithNameCommand extends AbstractCommand<EventApi> {

    private String eventName;

    /**
     * The identifier of the command
     *
     * @return the identifier of the command as a string
     */
    @Override
    public String command() {
        return "getEventWithName";
    }

    /**
     * Executes and processes the concrete
     * implementation of an {@link AbstractCommand}
     *
     * @throws Exception if the command fails or doesn't exist
     */
    @Override
    public void execute() throws Exception {
        Event event = shell.api.eventCatalogService.getEventWithName(eventName);

        // no events with this name have been found...
        if (event == null) {
            System.out.println("No event with the name " + eventName + " exists in the PolyEvent system!");
        }
        else {
            System.out.println("Found an event with the name " + eventName + ".");
            System.out.println("If multiple events had the same name, only the most recent event was selected.");
            System.out.println(new EventDTO(event).toString());
        }
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
        eventName = args.get(0);
    }

    /**
     * The method to call when the user wants to know the usage of this command
     *
     * @return the usage of this command, with a little description of the command
     */
    @Override
    public String helper() {
        return "Retrieves the event with the given name\nUsage: " + command() + " eventName";
    }
}

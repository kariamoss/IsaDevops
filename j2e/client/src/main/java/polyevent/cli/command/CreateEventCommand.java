package polyevent.cli.command;

import polyevent.Event;
import polyevent.RoomType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class that will represent the command
 * to create an event.
 * Needed args for an event creation are :
 *
 * <li>
 *     <ul>An event name</ul>
 *     <ul>An estimation of number of participants</ul>
 *     <ul>One or more type of rooms</ul>
 * </li>
 */
public class CreateEventCommand extends AbstractCommand<Void> {

    Event e;

    /**
     * The identifier of the command
     *
     * @return the identifier of the command as a string
     */
    @Override
    public String command() {
        return "createEvent";
    }

    @Override
    public void execute() throws Exception {

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
        String eventName = args.get(0);
        int estimatedPeopleNumber = Integer.parseInt(args.get(1));
        List roomsGeneric = Arrays.asList(args.get(2).split(" "));
        List<RoomType> rooms = new ArrayList<>();

        for (Object s : roomsGeneric)
            rooms.add(RoomType.valueOf((String) s));

        this.e = new Event(estimatedPeopleNumber, eventName, rooms);
    }
}

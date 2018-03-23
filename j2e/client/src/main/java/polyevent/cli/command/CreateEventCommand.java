package polyevent.cli.command;

import api.EventApi;
import polyevent.RoomType;

import java.util.Calendar;
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
public class CreateEventCommand extends AbstractCommand<EventApi> {

    private String eventName;
    private String email;
    private Calendar startDate;
    private int estimatedPeopleNumber;
    private List<RoomType> rooms;

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

        boolean result = shell.api.eventService.createEvent(eventName, estimatedPeopleNumber, startDate, email);
        System.out.println("Completion of event creation: " + result);
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
        estimatedPeopleNumber = Integer.parseInt(args.get(1));
        email = args.get(2);
        startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR_OF_DAY, 24); // + one day

        /*
        TODO keep this code here as it will be used after the MVP development
        estimatedPeopleNumber = Integer.parseInt(args.get(1));
        List roomsGeneric = Arrays.asList(args.get(2).split(" "));
        rooms = new ArrayList<>();

        for (Object s : roomsGeneric)
            rooms.add(RoomType.valueOf((String) s));

        */
    }

    /**
     * The method to call when the user wants to know the usage of this command
     *
     * @return the usage of this command, with a little description of the command
     */
    @Override
    public String helper() {
        return "Creates a new event at Polytech.\nUsage: createEvent eventName peopleNumber email ";
        //return "Creates a new event at Polytech.\nUsage: createEvent eventName peopleNumber roomType1 [roomTypes] ";
    }
}

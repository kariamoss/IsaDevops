package polyevent;

/**
 * Hello world!
 *
 */
public class EventOrganizer implements IEventOrganizer {

    @EJB private IRoomBooker roomBooker;


    @Override
    public boolean bookRoom(Event event) {
        return false;
    }
}

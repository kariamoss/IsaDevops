package polyevent;

import javax.ejb.Local;
import java.util.Calendar;

@Local
public interface IEventCreator {

    Event registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException;

    boolean cancelEvent(Event event);
}

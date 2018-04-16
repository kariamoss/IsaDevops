package polyevent;

import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.Local;
import java.util.Calendar;

@Local
public interface IEventCreator {

    polyevent.entities.Event registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException;

    boolean cancelEvent(Event event);
}

package polyevent;

import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;

import javax.ejb.Local;
import java.util.Calendar;

@Local
public interface IEventCreator {

    Event registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException;

    boolean cancelEvent(Coordinator coordinator, Event event) throws DataIntegrityException;
}

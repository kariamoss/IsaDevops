package polyevent;


import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    Event book(List<Room> rooms, Event event) throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException;

    boolean cancelRoomBooking(List<Room> rooms, Event event);
}

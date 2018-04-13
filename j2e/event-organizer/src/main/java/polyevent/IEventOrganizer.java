package polyevent;


import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException;

}

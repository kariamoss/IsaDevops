package polyevent;


import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException;

}

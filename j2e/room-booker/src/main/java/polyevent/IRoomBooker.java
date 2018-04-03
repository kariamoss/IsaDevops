package polyevent;


import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    Event book(List<Room> rooms, Event event) throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException;

    boolean cancelRoomBooking(List<Room> rooms, Event event);
}

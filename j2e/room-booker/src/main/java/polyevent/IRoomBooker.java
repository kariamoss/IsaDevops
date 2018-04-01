package polyevent;


import polyevent.communication.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    Message book(List<Room> rooms, Event event);

    boolean cancelRoomBooking(List<Room> rooms, Event event);
}

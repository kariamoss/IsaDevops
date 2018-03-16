package polyevent;


import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    boolean book(List<Room> rooms, Event event);

    boolean cancelRoomBooking(List<Room> rooms, Event event);
}

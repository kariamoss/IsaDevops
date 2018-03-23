package polyevent;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Hello world!
 *
 */
@Stateless
public class RoomBooker implements IRoomBooker
{
    @Override
    public boolean book(List<Room> rooms, Event event) {
        return false;
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

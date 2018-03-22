package polyevent;

import java.util.List;

/**
 * Hello world!
 *
 */
public class RoomBooker implements IRoomBooker
{
    @Override
    public boolean book(List<RoomType> rooms, Event event) {
        return false;
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

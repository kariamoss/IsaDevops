package polyevent;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Hello world!
 *
 */
@Stateless
public class RoomBooker implements IRoomBooker {

    @EJB protected Database memory;
    protected AgendaAPI api;

    public RoomBooker() {
        api = new AgendaAPI();
    }

    @Override
    public boolean book(List<Room> rooms, Event event) {

        if(rooms.isEmpty())
            return false;

        for(Room r : rooms)
            if (!api.bookRoom(r))
                return false;
        return memory.bookRoomsToEvent(event, rooms);
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

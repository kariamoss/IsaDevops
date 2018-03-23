package polyevent;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class RoomBooker implements IRoomBooker {

    @EJB protected Database memory;
    protected AgendaAPI api;
    private Logger l = Logger.getLogger(RoomBooker.class.getName());

    public RoomBooker() {
        api = new AgendaAPI();
    }

    @Override
    public boolean book(List<Room> rooms, Event event) {

        l.log(Level.INFO, "Received request for room booking");

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

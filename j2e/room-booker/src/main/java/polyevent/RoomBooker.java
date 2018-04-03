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
    public Event book(List<Room> rooms, Event event) throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {

        l.log(Level.INFO, "Received request for room booking");

        if(rooms.isEmpty()) {
            l.log(Level.SEVERE, "Can't book an empty list of rooms");
            throw new InvalidRoomException("The list of desired rooms cannot be empty");
        }

        for(Room r : rooms) {
            if (!api.bookRoom(r)) {
                throw new RoomNotAvailableException("One or more rooms are not available (they have already been booked)");
            }
        }

        if(!memory.bookRoomsToEvent(event, rooms)) {
            throw new DatabaseSavingException("Internal error while saving the rooms for the event in the database");
        }

        return event;
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

package polyevent;

import polyevent.communication.Message;

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
    public Message book(List<Room> rooms, Event event) {

        l.log(Level.INFO, "Received request for room booking");

        if(rooms.isEmpty()) {
            l.log(Level.SEVERE, "Can't book an empty list of rooms");
            return new Message()
                    .withStatus(400)
                    .withStatusText("The list of desired rooms cannot be empty")
                    .withTransmittedObject(new IllegalArgumentException("The list of desired rooms cannot be empty"));
        }

        for(Room r : rooms)
            if (!api.bookRoom(r))
                return new Message()
                        .withStatus(500)
                        .withStatusText("A problem occurred while reserving rooms with the external service EDT")
                        .withTransmittedObject(new IllegalArgumentException("A problem occurred while reserving rooms with the external service EDT"));
        if(!memory.bookRoomsToEvent(event, rooms)) {
            return new Message()
                    .withStatus(500)
                    .withStatusText("A problem occurred while reserving rooms with the database")
                    .withTransmittedObject(new IllegalArgumentException("A problem occurred while reserving rooms with the database"));
        }
        return new Message()
                .withStatus(200)
                .withStatusText("Successfully booked rooms for the event")
                .withTransmittedObject(event);
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

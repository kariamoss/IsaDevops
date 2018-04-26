package polyevent;

import polyevent.entities.Event;
import polyevent.entities.Room;

import java.io.Serializable;
import java.util.List;

public class BookingWrapper implements Serializable {
    private Event event;
    private List<Room> rooms;

    public BookingWrapper(Event event, List<Room> rooms) {
        this.event = event;
        this.rooms = rooms;
    }

    public Event getEvent() {
        return event;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}

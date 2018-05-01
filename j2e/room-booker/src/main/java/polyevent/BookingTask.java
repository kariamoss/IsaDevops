package polyevent;

import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingTask {
    private List<Room> rooms;
    private Event event;
    private AgendaAPI api;

    private Logger l = Logger.getLogger(BookingTask.class.getName());

    public BookingTask(List<Room> rooms, Event event) {
        this.rooms = rooms;
        this.event = event;
        api = new AgendaAPI();
    }

    public boolean book(){

        l.log(Level.INFO, "attempting room booking");

        if (rooms.isEmpty()) {
            l.log(Level.SEVERE, "Can't book an empty list of rooms");
            return true;
        }

        for (Room r : rooms) {
            if (!api.bookRoom(r)) {
                l.log(Level.SEVERE, "The reservation for the given room was not possible since it's already booked : " + r);
                return false;
            }
        }

        return true;
    }

    public boolean bindRoomsToEvent(EntityManager entityManager) {
        event.getRooms().addAll(rooms);
        for (Room r : rooms) {
            r.getEvents().add(event);
        }

        Event e = entityManager.merge(event);

        return e != null; // todo add this condition && e != event;
    }
}
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

    public BookingTask(List<Room> rooms, Event event, AgendaAPI api) {
        this.rooms = rooms;
        this.event = event;
        this.api = api;
    }

    public BookingTask(List<Room> rooms, Event event) {
        this(rooms,event,new AgendaAPI());
    }

    public boolean book(){

        l.log(Level.INFO, "attempting room booking");
        try {
            if (rooms.isEmpty()) {
                l.log(Level.SEVERE, "Can't book an empty list of rooms");
                return true;
            }

            for (Room r : rooms) {
                if (!api.bookRoom(r)) {
                    l.log(Level.SEVERE, "The reservation for the given room can't be done ");
                    return false;
                }
            }
        }catch (Exception e){
            l.log(Level.SEVERE, "something went wrong with the booking ");
            return false;
        }

        return true;
    }

    public boolean bindRoomsToEvent(EntityManager entityManager) {
        Event e = entityManager.find(Event.class, event.getId());

        l.log(Level.SEVERE, "event by id : " + e);

        if (e == null)
            return false;

        e.getRooms().addAll(rooms);
        for (Room r : rooms) {
            r.getEvents().add(e);
        }

        return true;
    }

    public BookingWrapper getWrapper(){
        return new BookingWrapper(this.event,this.rooms);
    }
}
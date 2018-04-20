package polyevent;

import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    @EJB protected IRoomBooker roomBooker;
    private Logger l = Logger.getLogger(EventOrganizer.class.getName());


    @Override
    public Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {

        l.log(Level.INFO, "Received request for room booking for event");

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(RoomType.MEETING_ROOM, 100, (new Random()).nextBoolean() ? "E+100" : "E+101"));

        return roomBooker.book(rooms, event);
    }
}

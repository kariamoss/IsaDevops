package polyevent;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    @EJB protected IRoomBooker roomBooker;


    @Override
    public boolean bookRoom(Event event) {


        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(RoomType.MEETING_ROOM, 100, (new Random()).nextBoolean() ? "E+103" : "E+101"));

        return roomBooker.book(rooms, event);
    }
}

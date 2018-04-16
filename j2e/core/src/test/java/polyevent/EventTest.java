package polyevent;


import org.junit.Before;
import org.junit.Test;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;

import static org.junit.Assert.assertTrue;

public class EventTest {
    private Event event;

    @Before
    public void init(){
        this.event = new Event(100,"salut");
    }

    @Test
    public void roomAddTest(){
        Room room = new Room(RoomType.MEETING_ROOM,100,"test");
        this.event.addRoom(room);
        assertTrue(this.event.getRooms().contains(room));
    }
}

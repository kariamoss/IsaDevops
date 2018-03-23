package polyevent;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class EventTest {
    private Event event;

    @Before
    public void init(){
        this.event = new Event(100,"salut",new ArrayList<RoomType>());
    }

    @Test
    public void roomAddTest(){
        Room room = new Room(RoomType.MEETING_ROOM,100,"test");
        this.event.addRoom(room);
        assertTrue(this.event.getRooms().contains(room));
    }
}

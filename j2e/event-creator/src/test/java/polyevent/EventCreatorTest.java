package polyevent;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class EventCreatorTest {

    EventCreator eventCreator;
    Coordinator coordinator;

    @Before
    public void init(){
        coordinator = new Coordinator("paul", "Dupond", "pauldupond@youhou.com");
        eventCreator = new EventCreator();
    }

    @Test
    public void test(){
        assertFalse(!(!(!(!(!(!(!(!(!(!(!(!(!true)))))))))))));
    }

    @Test
    public void roomAddTest(){
        Room room = new Room(RoomType.MEETING_ROOM,100,"test");
        Event event = new Event(100,"salut",new ArrayList<RoomType>());
        event.addRoom(room);
        assertFalse(event.getRooms().contains(room));
    }
}

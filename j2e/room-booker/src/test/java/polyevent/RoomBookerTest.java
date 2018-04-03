package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class RoomBookerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IRoomBooker.class.getPackage());
    }

    private IRoomBooker roomBooker;
    private Database memory;
    private AgendaAPI api;

    private Event event;
    private List<Room> rooms;

    @Before
    public void init(){

        roomBooker = new RoomBooker();

        memory = Mockito.mock(Database.class);
        ((RoomBooker)roomBooker).memory = memory;

        api = Mockito.mock(AgendaAPI.class);
        ((RoomBooker)roomBooker).api = api;

        event = Mockito.mock(Event.class);
        rooms = new ArrayList<>();
    }

    @Test(expected = InvalidRoomException.class)
    public void bookEmptyRooms() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException {
        rooms.clear();
        assertNull(roomBooker.book(rooms, event));
    }

    @Test(expected = DatabaseSavingException.class)
    public void databaseFail() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException {
        rooms.clear();
        Room room = mock(Room.class);
        rooms.add(room);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(false);
        when(api.bookRoom(room)).thenReturn(true);
        assertNull(roomBooker.book(rooms, event));
    }

    @Test
    public void allOk() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException {
        rooms.clear();
        Room room = mock(Room.class);
        rooms.add(room);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(true);
        when(api.bookRoom(room)).thenReturn(true);
        assertNotNull(roomBooker.book(rooms, event));
    }

    @Test(expected = RoomNotAvailableException.class)
    public void roomServiceFail() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException {
        rooms.clear();
        Room room1 = mock(Room.class);
        rooms.add(room1);
        Room room2 = mock(Room.class);
        rooms.add(room2);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(true);
        when(api.bookRoom(room1)).thenReturn(true);
        when(api.bookRoom(room2)).thenReturn(false);
        assertNull(roomBooker.book(rooms, event));
    }
}

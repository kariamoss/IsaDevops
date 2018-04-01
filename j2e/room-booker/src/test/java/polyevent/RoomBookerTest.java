package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
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

    @Test
    public void bookEmptyRooms() {
        rooms.clear();
        assertTrue(roomBooker.book(rooms, event).isNotOk());
    }

    @Test
    @Ignore // todo fix
    public void databaseFail() {
        rooms.clear();
        Room room = mock(Room.class);
        rooms.add(room);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(false);
        when(api.bookRoom(room)).thenReturn(true);
        assertTrue(roomBooker.book(rooms, event).isNotOk());
    }

    @Test
    public void allOk() {
        rooms.clear();
        Room room = mock(Room.class);
        rooms.add(room);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(true);
        when(api.bookRoom(room)).thenReturn(true);
        assertTrue(roomBooker.book(rooms, event).isOk());
    }

    @Test
    public void roomServiceFail() {
        rooms.clear();
        Room room1 = mock(Room.class);
        rooms.add(room1);
        Room room2 = mock(Room.class);
        rooms.add(room2);
        when(memory.bookRoomsToEvent(event, rooms)).thenReturn(true);
        when(api.bookRoom(room1)).thenReturn(true);
        when(api.bookRoom(room2)).thenReturn(false);
        assertTrue(roomBooker.book(rooms, event).isNotOk());
    }
}

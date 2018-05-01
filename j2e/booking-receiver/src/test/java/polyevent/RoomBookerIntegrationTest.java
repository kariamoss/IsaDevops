package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
@Category(IntegrationTests.class)
public class RoomBookerIntegrationTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(BookingReceiver.class.getPackage())
                .addPackage(Event.class.getPackage())
                .addPackage(Room.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }


    @PersistenceContext
    private EntityManager entityManager;

    private Coordinator coordinator;
    private Event event;
    private List<Room> rooms;

    @Before
    public void init(){
        coordinator = new Coordinator("Maxime", "Flament", "maximeflam@gmail.com", "abcd");
        event = new Event(coordinator, 100, "Event");

        rooms = new ArrayList<>();
    }

    @Test(expected = InvalidRoomException.class)
    @Ignore
    public void bookEmptyRooms() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        rooms.clear();
     }

    @Test(expected = DatabaseSavingException.class)
    @Ignore
    public void databaseFail() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        rooms.clear();
        Room room = new Room(RoomType.MEETING_ROOM, 100, "E+100");
        rooms.add(room);

    }

    @Test
    @Ignore
    public void allOk() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        rooms.clear();
        Room room = new Room(RoomType.MEETING_ROOM, 100, "E+101");
        rooms.add(room);

    }

    @Test(expected = RoomNotAvailableException.class)
    @Ignore
    public void roomServiceFail() throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        rooms.clear();
        Room room1 = new Room(RoomType.MEETING_ROOM, 100, "E+102");
        rooms.add(room1);
        Room room2 = new Room(RoomType.MEETING_ROOM, 100, "E+103");
        rooms.add(room2);

    }
}

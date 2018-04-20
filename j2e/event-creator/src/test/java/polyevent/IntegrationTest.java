package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.EJB;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class IntegrationTest {

    @EJB
    private IEventCreator eventCreator;


    @EJB
    private Database memory;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addPackage(Room.class.getPackage())
                .addPackage(IEventCreator.class.getPackage())
                .addPackage(IEventOrganizer.class.getPackage())
                .addPackage(IRoomBooker.class.getPackage())
                .addPackage(EventCreator.class.getPackage())
                .addPackage(EventOrganizer.class.getPackage())
                .addPackage(RoomBooker.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @Test
    @Ignore
    public void eventCreationTest() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 10);

        Event e = eventCreator.registerEvent("Eventname", 30, cal, new Coordinator("Jehan", "Lelama", "lelamadu06@msn.fr"));

        assertNotNull(e);

        Event event = memory.findEventByName("Eventname");

        assertNotNull(event);
        assertTrue(!event.getRooms().isEmpty());

    }
}

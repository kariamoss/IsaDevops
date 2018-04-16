package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class EventCatalogTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCatalog.class.getPackage())
                .addPackage(EventCatalog.class.getPackage())
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @EJB
    private IEventCatalog eventCatalog;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Coordinator c;

    private Event e1;
    private Event e2;

    private Room r1;
    private Room r2;

    private String lookUpEventName;

    @Before
    public void setUpMocks() {
        lookUpEventName = "Event1";

        e1 = new Event(c, 100, lookUpEventName);
        e2 = new Event(c, 10, "Event2");

        r1 = new Room(RoomType.MEETING_ROOM, 100, "E+100");
        r2 = new Room(RoomType.MEETING_ROOM, 20, "E+101");

        e1.addRoom(r1);
        e2.addRoom(r2);

        c = new Coordinator("Maxime", "Flament", "maximeflam@gmail.com", "abcd");
        c.addEvent(e1);
        c.addEvent(e2);
    }

    @Test
    public void testGetAllEvents() {
        entityManager.persist(e1);
        entityManager.persist(e2);
        entityManager.persist(c);
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals("Size of event list should be equal to 2", optionalEvents.get().size(), 2);
        assertEquals(optionalEvents.get().get(0).getName(), lookUpEventName);
    }

    @Test
    @Ignore
    public void testFindEventWithName() {
        entityManager.persist(c);
        Optional<Event> optionalEvent = eventCatalog.getEventWithName(lookUpEventName);
        assertTrue(optionalEvent.isPresent());
        assertEquals(optionalEvent.get().getName(), lookUpEventName);
    }

    @Test
    @Ignore
    public void testGetAllEventsEmpty() {
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals(optionalEvents.get().size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    @Ignore
    public void testFindEventWithNameEmpty() {
        Optional<Event> optionalEvent = eventCatalog.getEventWithName(lookUpEventName);
        assertFalse(optionalEvent.isPresent());
        Object whatever = optionalEvent.get(); // throws the NoSuchElementException
    }

    @Test
    @Ignore
    public void testFindEventNameConstraintViolationNull() {
        //todo
    }

    @Test
    @Ignore
    public void testFindEventNameConstraintViolationEmpty() {
        //todo
    }
}

package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class EventCatalogTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCatalog.class.getPackage())
                .addPackage(EventCatalog.class.getPackage())
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addPackage(Room.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @EJB
    private IEventCatalog eventCatalog;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Coordinator c;

    private String coordinatorEmail;

    private Event e1;
    private Event e2;

    private Room r1;
    private Room r2;

    private String lookUpEventName;
    private String badLookupEventName;

    @Before
    public void setUpMocks() {
        lookUpEventName = "Event1";
        badLookupEventName = "Event2";
        coordinatorEmail = "maximeflam@gmail.com";

        c = new Coordinator("Maxime", "Flament", coordinatorEmail, "abcd");

        e1 = new Event(c, 100, lookUpEventName);
        e2 = new Event(c, 10, badLookupEventName);

        r1 = new Room(RoomType.MEETING_ROOM, 100, "E+100");
        r2 = new Room(RoomType.MEETING_ROOM, 20, "E+101");

        e1.getRooms().add(r1);
        e1.getRooms().add(r2);

        e2.getRooms().add(r1);

        c.getEventsCreated().add(e1);
        c.getEventsCreated().add(e2);

        e1.setCoordinator(c);
        e2.setCoordinator(c);

        entityManager.persist(c);
    }

    /**
     * Tests that the {@link Event} objects associated to the {@link Coordinator}
     * have been stored in the database by transitivity (OneToMany relationship)
     * by using the {@link EventCatalog} events lookup method
     *
     * @see EventCatalog#getAllEvents()
     * @see Coordinator#eventsCreated
     */
    @Test
    public void testGetAllEvents() {
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        List<Event> events = optionalEvents.get();
        assertEquals("Size of event list should be equal to 2", events.size(), 2);
        assertEquals(events.get(0).getName(), lookUpEventName);

        // tests that the rooms have persisted
        assertEquals(2, events.get(0).getRooms().size());
        assertEquals(r1, events.get(0).getRooms().get(0));
        assertEquals(r2, events.get(0).getRooms().get(1));
        assertEquals(1, events.get(1).getRooms().size());
        assertEquals(r1, events.get(1).getRooms().get(0));
    }

    /**
     * Tests that the {@link Event} with the name {@link #lookUpEventName} {@link #e1}
     * object associated to the {@link Coordinator}
     * has been stored in the database by transitivity (OneToMany relationship)
     * by using the {@link EventCatalog} event name lookup method
     *
     * @see EventCatalog#getEventWithName(String)
     * @see Coordinator#eventsCreated
     */
    @Test
    public void testFindEventWithName() throws InvalidRequestParametersException {
        Optional<Event> optionalEvent = eventCatalog.getEventWithName(lookUpEventName);
        assertTrue(optionalEvent.isPresent());
        assertEquals(optionalEvent.get().getName(), lookUpEventName);
    }

    @Test
    public void testFindEventWithNameConflict() throws InvalidRequestParametersException {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        end.add(Calendar.HOUR_OF_DAY, 3);

        Coordinator coord = entityManager.find(Coordinator.class, c.getId());
        Event event = new Event(coord, start.getTime(), end.getTime(), 11, lookUpEventName);
        entityManager.persist(event);
        coord.getEventsCreated().add(event);
        Optional<List<Event>> events = eventCatalog.getAllEvents();
        assertTrue(events.isPresent());
        assertEquals(3, events.get().size());

        Optional<Event>  eventOptional = eventCatalog.getEventWithName(lookUpEventName);
        assertTrue(eventOptional.isPresent());
        assertEquals(event, eventOptional.get());
        assertNotEquals(e1, eventOptional.get());

        entityManager.remove(event);
        c.getEventsCreated().remove(event);
    }

    /**
     * Tests that after the removal of both events from the coordinator,
     * the {@link Event} objects associated to the {@link Coordinator}
     * have been successfully removed from the database, thus, that the
     * list of results is empty
     *
     * @see EventCatalog#getAllEvents()
     * @see Coordinator#eventsCreated
     */
    @Test
    public void testGetAllEventsEmpty() {
        c.setEventsCreated(new ArrayList<>());
        e1.setCoordinator(null);
        e2.setCoordinator(null);

        entityManager.remove(e1);
        entityManager.remove(e2);
        e1 = null;
        e2 = null;

        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals(optionalEvents.get().size(), 0);


    }

    /**
     * Tests that the removal of the {@link Coordinator}
     * {@link #c} from the database also removes the associated events,
     * and thus the find method doesn't return wrong results
     */
    @Test
    public void testDestroyCoordinatorCascade() throws Exception {

        int cId = c.getId();
        int e1Id = e1.getId();
        int e2Id = e2.getId();

        c.setEventsCreated(new ArrayList<>());
        e1.setCoordinator(null);
        e2.setCoordinator(null);
        entityManager.remove(e1);
        entityManager.remove(e2);
        e1 = null;
        e2 = null;
        entityManager.remove(c);
        c = null;

        assertTrue(eventCatalog.getAllEvents().isPresent());
        assertTrue(eventCatalog.getAllEvents().get().isEmpty());
        assertNull(entityManager.find(Coordinator.class, cId));
        assertNull(entityManager.find(Event.class, e1Id));
        assertNull(entityManager.find(Event.class, e2Id));
    }

    /**
     * Utility method used to retrieve a Coordinator from the database
     * This method is used in the {@link @After} method to retrieve the coordinator
     * {@link #c} and recreate it in the database after ech test, since the execution order
     * of the methods inside test suites is not guaranteed to remain the same
     * over 2 executions
     * @return the {@link Coordinator} that was created for this test suite
     */
    private Optional<Coordinator> findCoordinator(String email) {
        // todo remove this method and use the CoordinatorRegistry to retrieve the Coordinator in the @After method
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coordinator> criteria = builder.createQuery(Coordinator.class);
        Root<Coordinator> root =  criteria.from(Coordinator.class);
        criteria.select(root).where(builder.equal(root.get("email"), email));
        TypedQuery<Coordinator> query = entityManager.createQuery(criteria);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            return Optional.empty();
        }
    }

    @After
    public void cleanUp() throws Exception {

        if (c != null)
            c.setEventsCreated(new ArrayList<>());

        if (e1 != null)
            e1.setCoordinator(null);

        if (e2 != null)
            e2.setCoordinator(null);

        userTransaction.begin();
            Optional<Coordinator> optionalCoordinator = this.findCoordinator(coordinatorEmail);
            if (optionalCoordinator.isPresent()) {
                Coordinator coordinator = optionalCoordinator.get();
                entityManager.refresh(coordinator);
                entityManager.remove(coordinator);
                c = null;
            }
        userTransaction.commit();
    }
}

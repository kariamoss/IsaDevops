package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

    @Resource
    private UserTransaction userTransaction;

    private Coordinator c;

    private Event e1;
    private Event e2;

    private String lookUpEventName;

    @Before
    public void setUpMocks() {
        lookUpEventName = "Event1";

        c = new Coordinator("Maxime", "Flament", "maximeflam@gmail.com", "abcd");
        e1 = new Event(c, 100, lookUpEventName);
        e2 = new Event(c, 10, "Event2");

        entityManager.persist(c);
        entityManager.persist(e1);
        entityManager.persist(e2);
    }

    @Test
    public void testGetAllEvents() {
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals("Size of event list should be equal to 2", optionalEvents.get().size(), 2);
        assertEquals(optionalEvents.get().get(0).getName(), lookUpEventName);
    }

    @Test
    public void testFindEventWithName() {
        Optional<Event> optionalEvent = eventCatalog.getEventWithName(lookUpEventName);
        assertTrue(optionalEvent.isPresent());
        assertEquals(optionalEvent.get().getName(), lookUpEventName);
    }

    @Test
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

    @After
    public void cleanSetUp() throws Exception {
        userTransaction.begin();
            c = entityManager.merge(c);
            entityManager.remove(c);
            c = null;
            e1 = entityManager.merge(e1);
            entityManager.remove(e1);
            e1 = null;
            e2 = entityManager.merge(e2);
            entityManager.remove(e2);
            e2 = null;
        userTransaction.commit();
    }
}

package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class IntegrationTest {

    @EJB
    private IEventCatalog eventCatalog;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Test
    @Ignore
    public void eventListingTest() throws InvalidRequestParametersException {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 10);

        Coordinator c = new Coordinator("Jehan", "Lelama", "lelamadu06@msn.fr");
        Event e = new Event(c, cal.getTime(), cal.getTime(), 30, "Eventname");

        c.getEventsCreated().add(e);

        // will persist the events as well
        entityManager.persist(c);

        Optional<Event> result = eventCatalog.getEventWithName("Eventname");

        assertTrue(result.isPresent());
        assertEquals(e, result.get());
    }
}

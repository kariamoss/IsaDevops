package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.ejb.EJB;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class IntegrationTest {

    @EJB
    private IEventCatalog eventCatalog;

    @EJB
    private Database memory;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCatalog.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @Test
    @Ignore
    public void eventListingTest() throws InvalidRequestParametersException {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 10);

        memory.addEvent(new Event(new Coordinator("Jehan", "Lelama", "lelamadu06@msn.fr"), cal.getTime(), cal.getTime(), 30, "Eventname"));

        Optional<Event> result = eventCatalog.getEventWithName("Eventname");

        assertTrue(result.isPresent());
    }
}

package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Calendar;

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
                .addPackage(IEventCreator.class.getPackage())
                .addPackage(IEventOrganizer.class.getPackage())
                .addPackage(IRoomBooker.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @Test
    public void test() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 10);

        boolean result = eventCreator.registerEvent("Eventname", 30, cal, new Coordinator("Jehan", "Lelama", "lelamadu06@msn.fr"));

        assertTrue(result);

        Event event = memory.getEventByName("Eventname");

        assertTrue(event != null);
        //assertTrue(!event.getRooms().isEmpty());

    }
}

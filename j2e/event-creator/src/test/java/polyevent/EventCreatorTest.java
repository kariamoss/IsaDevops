package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.Calendar;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class EventCreatorTest {

    @Deployment
    public static WebArchive createDeployment() {
        System.out.println("HELLO!!!!");
        return ShrinkWrap.create(WebArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCreator.class.getPackage())
                .addPackage(IEventOrganizer.class.getPackage())
                .addPackage(EventCreator.class.getPackage())
                .addPackage(EventOrganizer.class.getPackage())
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addPackage(Room.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @EJB
    private IEventCreator eventCreator;
    private IEventOrganizer eventOrganizer;

    @Inject
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;

    private Coordinator coordinator;
    private Event e;
    private String coordinatorEmail;

    private Calendar startDate;


   @Test
    public void tarace() {}
}

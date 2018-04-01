package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import polyevent.communication.Message;

import javax.ejb.EJB;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class EventCreatorTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCreator.class.getPackage())
                .addPackage(IEventOrganizer.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @EJB
    private IEventCreator eventCreator;

    private IEventOrganizer eventOrganizer;
    private Database database;

    private Coordinator coordinator;
    private Calendar startDate;

    @Before
    public void init(){
        coordinator = new Coordinator("paul", "Dupond", "pauldupond@youhou.com");
        startDate = Calendar.getInstance();

        // we need to instantiate the EJBs in order for mocks/spies to work
        // within an EJB container
        eventCreator = new EventCreator();

        // mocks the call to IEventOrganizer.bookRoom(Room) in order to return true
        // as we want to unit test the IEventCreator component only
        eventOrganizer = Mockito.mock(EventOrganizer.class);
        when(eventOrganizer.bookRoom(notNull(Event.class)))
                .thenReturn(
                        new Message()
                                .withStatus(200)
                                .withStatusText("Successfully booked rooms for the event")
                                .withTransmittedObject(null)
                );

        database = Mockito.spy(Database.class);
        doNothing().when(database).addEvent(notNull(Event.class));

        ((EventCreator) eventCreator).eventOrganizer = eventOrganizer;
        ((EventCreator) eventCreator).memory = database;
    }

    /**
     * Creates a new event with good parameters
     * This test should create the event with no problem
     * and return a valid result
     */
    @Test
    public void goodEventCreation() {

        Calendar startDateTwo = Calendar.getInstance();
        startDateTwo.add(Calendar.HOUR_OF_DAY, 10);

        Message shouldSucceed = eventCreator.registerEvent("toto", 10, startDateTwo, coordinator);
        assertTrue(shouldSucceed.isOk());
    }

    /**
     * Registers a new event with a negative number of people
     * The registration should fail because a negative number of people is impossible
     */
    @Test
    public void eventCreationWithBadPeopleNumber() {
        Message shouldNotSucceed = eventCreator.registerEvent("toto", -10, startDate, coordinator);
        assertTrue(shouldNotSucceed.isNotOk());
    }

    /**
     * Registers a new event with an empty name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNoName() {
        Message shouldNotSucceed = eventCreator.registerEvent("", 10, startDate, coordinator);
        assertTrue(shouldNotSucceed.isNotOk());
    }

    /**
     * Registers a new event with a null name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNullName() {
        Message shouldNotSucceed = eventCreator.registerEvent(null, 10, startDate, coordinator);
        assertTrue(shouldNotSucceed.isNotOk());
    }

    /**
     * Registers a new event with a starting date that isn't valid, i.e.,
     * a date in the past
     * The registration should fail because an event that already happened or
     * has a date that has been passed shouldn't be registered (that's not logical)
     */
    @Test
    public void eventCreationWithAlreadyPassedDate() {
        Calendar badStartDate = Calendar.getInstance();
        badStartDate.add(Calendar.HOUR_OF_DAY, -12);
        Message shouldNotSucceed = eventCreator.registerEvent(null, 10, badStartDate, coordinator);
        assertTrue(shouldNotSucceed.isNotOk());
    }

    /**
     * Registers a new event with a null coordinator
     * The registration should fail because an event is always associated to a coordinator
     */
    @Test
    public void eventCreationWithNullCoordinator() {
        Message shouldNotSucceed = eventCreator.registerEvent("toto", 10, startDate, null);
        assertTrue(shouldNotSucceed.isNotOk());
    }
}

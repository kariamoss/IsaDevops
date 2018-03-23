package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;


import javax.ejb.EJB;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class EventCreatorTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCreator.class.getPackage());
    }

    @EJB
    private IEventCreator eventCreator;

    @EJB
    private IEventOrganizer eventOrganizer;

    private Coordinator coordinator;
    private Calendar startDate;

    @Before
    public void init(){
        coordinator = new Coordinator("paul", "Dupond", "pauldupond@youhou.com");
        eventCreator = new EventCreator();
        startDate = Calendar.getInstance();
        //when(eventOrganizer.bookRoom())
    }

    /**
     * Creates a new event with good parameters
     * This test should create the event with no problem
     * and return a valid result
     */
    @Test
    @Ignore
    public void goodEventCreation() {
        // mocks the call to IEventOrganizer.bookRoom(Room) in order to return true
        // as we want to unit test the IEventCreator component only
        eventOrganizer = Mockito.mock(EventOrganizer.class);
        Event e = Mockito.mock(Event.class);
        when(eventOrganizer.bookRoom(e)).thenReturn(true);

        boolean shouldSucceed = eventCreator.registerEvent("toto", 10, startDate, coordinator);
        assertTrue(shouldSucceed);
    }

    /**
     * Registers a new event with a negative number of people
     * The registration should fail because a negative number of people is impossible
     */
    @Test
    public void eventCreationWithBadPeopleNumber() {
        boolean shouldNotSucceed = eventCreator.registerEvent("toto", -10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with an empty name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNoName() {
        boolean shouldNotSucceed = eventCreator.registerEvent("", 10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with a null name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNullName() {
        boolean shouldNotSucceed = eventCreator.registerEvent(null, 10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
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
        boolean shouldNotSucceed = eventCreator.registerEvent(null, 10, badStartDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with a null coordinator
     * The registration should fail because an event is always associated to a coordinator
     */
    @Test
    public void eventCreationWithNullCoordinator() {
        boolean shouldNotSucceed = eventCreator.registerEvent("toto", 10, startDate, null);
        assertFalse(shouldNotSucceed);
    }
}

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

import javax.ejb.EJB;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    public void init() throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        coordinator = new Coordinator("paul", "Dupond", "MarcDu06@laposte.fr");
        startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR_OF_DAY, 10);

        // we need to instantiate the EJBs in order for mocks/spies to work
        // within an EJB container
        eventCreator = new EventCreator();

        // mocks the call to IEventOrganizer.bookRoom(Room) in order to return true
        // as we want to unit test the IEventCreator component only
        eventOrganizer = Mockito.mock(EventOrganizer.class);
        when(eventOrganizer.bookRoom(notNull(Event.class))).thenReturn(new Event());

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
    public void goodEventCreation() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Event e = eventCreator.registerEvent("toto", 10, startDate, coordinator);
        assertNotNull(e);
    }

    /**
     * Registers a new event with a negative number of people
     * The registration should fail because a negative number of people is impossible
     */
    @Test(expected = InvalidRequestParametersException.class)
    public void eventCreationWithBadPeopleNumber() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Event e = eventCreator.registerEvent("toto", -10, startDate, coordinator);
        assertNull(e);
    }

    /**
     * Registers a new event with an empty name
     * The registration should fail because an event should always have a name
     */
    @Test(expected = InvalidRequestParametersException.class)
    public void eventCreationWithNoName() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Event e = eventCreator.registerEvent("", 10, startDate, coordinator);
        assertNull(e);
    }

    /**
     * Registers a new event with a null name
     * The registration should fail because an event should always have a name
     */
    @Test(expected = InvalidRequestParametersException.class)
    public void eventCreationWithNullName() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Event e = eventCreator.registerEvent(null, 10, startDate, coordinator);
        assertNull(e);
    }

    /**
     * Registers a new event with a starting date that isn't valid, i.e.,
     * a date in the past
     * The registration should fail because an event that already happened or
     * has a date that has been passed shouldn't be registered (that's not logical)
     */
    @Test(expected = InvalidRequestParametersException.class)
    public void eventCreationWithAlreadyPassedDate() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Calendar badStartDate = Calendar.getInstance();
        badStartDate.add(Calendar.HOUR_OF_DAY, -12);
        Event e = eventCreator.registerEvent(null, 10, badStartDate, coordinator);
        assertNull(e);
    }

    /**
     * Registers a new event with a null coordinator
     * The registration should fail because an event is always associated to a coordinator
     */
    @Test(expected = InvalidRequestParametersException.class)
    public void eventCreationWithNullCoordinator() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        Event e = eventCreator.registerEvent("toto", 10, startDate, null);
        assertNull(e);
    }
}

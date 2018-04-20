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
import org.mockito.Mockito;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

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
import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

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


    @Before
    public void init() throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {
        System.out.println("HELLO!!!");
        coordinatorEmail = "MarcDu06@laposte.fr";
        coordinator = new Coordinator("paul", "Dupond", coordinatorEmail);
        startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR_OF_DAY, 10);

        e = new Event(coordinator, 100, "Event");

        coordinator.addEvent(e);

        // we need to instantiate the EJBs in order for mocks/spies to work
        // within an EJB container
        eventCreator = new EventCreator();

        // mocks the call to IEventOrganizer.bookRoom(Room) in order to return true
        // as we want to unit test the IEventCreator component only
        eventOrganizer = Mockito.mock(IEventOrganizer.class);
        when(eventOrganizer.bookRoom(notNull(Event.class))).thenReturn(e);

        entityManager.persist(coordinator);

        ((EventCreator) eventCreator).eventOrganizer = eventOrganizer;
        System.out.println("eventCreator = " + eventCreator);
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


    /**
     * Utility method used to retrieve a Coordinator from the database
     * This method is used in the {@link @After} method to retrieve the coordinator
     * {@link #coordinator} and recreate it in the database after ech test, since the execution order
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
        userTransaction.begin();
        Optional<Coordinator> optionalCoordinator = this.findCoordinator(coordinatorEmail);
        if (optionalCoordinator.isPresent()) {
            Coordinator coordinator = optionalCoordinator.get();
            entityManager.refresh(coordinator);
            entityManager.remove(coordinator);
            coordinator = null;
        }
        userTransaction.commit();
    }
}

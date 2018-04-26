package webservice.event;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.*;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.*;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class EventCreatorServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCreatorService.class.getPackage())
                .addPackage(IEventCreator.class.getPackage())
                .addPackage(ICoordinatorAuthenticator.class.getPackage())
                .addPackage(ICoordinatorRegistrator.class.getPackage())
                .addPackage(CoordinatorAccountsOperator.class.getPackage())
                .addPackage(EventCreatorService.class.getPackage())
                .addPackage(EventCreator.class.getPackage())
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addPackage(Room.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @EJB
    private IEventCreatorService eventService;
    @EJB
    private IEventCreator eventCreator;
    @EJB
    private ICoordinatorAuthenticator coordinatorAuthenticator;


    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserTransaction userTransaction;

    private Coordinator coordinatorFalseMail;
    private Coordinator coordinatorMail;
    private Calendar dateBegin;

    @Before
    public void setUpContext() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        coordinatorFalseMail = new Coordinator("Marc", "Jourdes", "MarcJourdes@free.fr", "password");
        coordinatorMail = new Coordinator("Marc", "Jourdes", "MarcDu06@laposte.fr", "password");

        entityManager.persist(coordinatorMail);

        dateBegin = Calendar.getInstance();
        dateBegin.setTime(new Date(2019, 10 , 10, 10, 15));

        eventService = new EventCreatorService();
        eventCreator = mock(EventCreator.class);
        when(eventCreator.registerEvent
                (eq("test"), eq(10), any(Calendar.class), any(Coordinator.class)))
                .thenReturn(
                        new Event()
                );
        ((EventCreatorService) eventService).eventCreator = eventCreator;
        ((EventCreatorService) eventService).coordinatorAuthenticator = coordinatorAuthenticator;
    }

    /**
     * Call EventCreation with not existing Coordinator
     */
    @Test(expected = InvalidCredentialsException.class)
    public void falseMailTest() throws InvalidCredentialsException, InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        eventService.createEvent("test", 10, dateBegin, coordinatorFalseMail);
    }

    /**
     * Call EventCreation with existing Coordinator
     */
    @Test
    public void correctMailTest() throws InvalidCredentialsException, InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        assertNotNull(eventService.createEvent("test", 10, dateBegin, coordinatorMail));
    }

    private Coordinator findCoordinator(String email, String password) throws InvalidCredentialsException, InvalidRequestParametersException {
        // todo remove this method and use the CoordinatorRegistry to retrieve the Coordinator in the @After method
        return coordinatorAuthenticator.authenticate(email, password);
    }

    @After
    public void cleanUp() throws Exception {
        userTransaction.begin();
        Coordinator coordinator = this.findCoordinator(coordinatorMail.getEmail(), coordinatorMail.getPassword());
        if (coordinator != null) {
            entityManager.refresh(coordinator);
            entityManager.remove(coordinator);
            coordinatorMail = null;
        }
        userTransaction.commit();
    }
}

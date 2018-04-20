package polyevent.event;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.Database;
import polyevent.EventCreator;
import polyevent.IEventCreator;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;
import webservice.event.EventCreatorService;
import webservice.event.IEventCreatorService;

import javax.ejb.EJB;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(Arquillian.class)
public class EventCreatorServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCreatorService.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @EJB private IEventCreatorService eventService;
    @EJB private Database memory;
    @EJB private IEventCreator eventCreator;

    private String coordinatorFalseMail;
    private String coordinatorMail;
    private Calendar dateBegin;
    private Database database;

    @Before
    public void setUpContext() throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        coordinatorFalseMail = "MarcJourdes@free.fr";
        coordinatorMail = "MarcDu06@laposte.fr";
        dateBegin = Calendar.getInstance();
        dateBegin.setTime(new Date(2019, 10 , 10, 10, 15));

        eventService = new EventCreatorService();
        eventCreator = mock(EventCreator.class);
        database = spy(Database.class);
        when(eventCreator.registerEvent
                (eq("test"), eq(10), any(Calendar.class), any(Coordinator.class)))
                .thenReturn(
                        new Event()
                );
        ((EventCreatorService) eventService).eventCreator = eventCreator;
        ((EventCreatorService) eventService).memory = database;
    }

    /**
     * Call EventCreation with not existing Coordinator
     */
    @Test(expected = InvalidCredentialsException.class)
    public void falseMailTest() throws InvalidCredentialsException, InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        assertNull(memory.getCoordinatorByMail(coordinatorFalseMail));
        assertNull(eventService.createEvent("test", 10, dateBegin, coordinatorFalseMail));
    }

    /**
     * Call EventCreation with existing Coordinator
     */
    @Test
    public void correctMailTest() throws InvalidCredentialsException, InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {
        assertNotNull(memory.getCoordinatorByMail(coordinatorMail));
        assertNotNull(eventService.createEvent("test", 10, dateBegin, coordinatorMail));
    }
}

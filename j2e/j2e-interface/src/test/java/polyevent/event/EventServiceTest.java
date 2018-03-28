package polyevent.event;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.Coordinator;
import polyevent.Database;
import polyevent.EventCreator;
import polyevent.IEventCreator;
import webservice.event.EventService;
import webservice.event.IEventService;

import javax.ejb.EJB;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(Arquillian.class)
public class EventServiceTest{

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventService.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @EJB private IEventService eventService;
    @EJB private Database memory;
    @EJB private IEventCreator eventCreator;

    private String coordinatorFalseMail;
    private String coordinatorMail;
    private Calendar dateBegin;
    private Database database;

    @Before
    public void setUpContext(){
        coordinatorFalseMail = "MarcJourdes@free.fr";
        coordinatorMail = "MarcDu06@laposte.fr";
        dateBegin = Calendar.getInstance();
        dateBegin.setTime(new Date(2019, 10 , 10, 10, 15));

        eventService = new EventService();
        eventCreator = mock(EventCreator.class);
        database = spy(Database.class);
        when(eventCreator.registerEvent
                (eq("test"), eq(10), any(Calendar.class), any(Coordinator.class))).thenReturn(true);
        ((EventService) eventService).eventCreator = eventCreator;
        ((EventService) eventService).memory = database;
    }

    /**
     * Call EventCreation with not existing Coordinator
     */
    @Test
    public void falseMailTest(){
        assertNull(memory.getCoordinatorByMail(coordinatorFalseMail));
        assertFalse(eventService.createEvent("test", 10, dateBegin, coordinatorFalseMail));
    }

    /**
     * Call EventCreation with existing Coordinator
     */
    @Test
    public void correctMailTest(){
        assertNotNull(memory.getCoordinatorByMail(coordinatorMail));

        assertTrue(eventService.createEvent("test", 10, dateBegin, coordinatorMail));
    }
}

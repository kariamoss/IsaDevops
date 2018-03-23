package polyevent;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import webservice.EventService;
import webservice.IEventService;

import javax.ejb.EJB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

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

    private String coordinatorFalseMail;
    private String coordinatorMail;
    private XMLGregorianCalendar dateBegin;

    @Before
    public void setUpContext(){
        coordinatorFalseMail = "MarcJourdes@free.fr";
        coordinatorMail = "MarcDu06@laposte.fr";
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date(2019, 10 , 10, 10, 15));
        dateBegin = null;
        try {
            dateBegin = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void falseMailTest(){
        assertNull(memory.getCoordinatorByMail(coordinatorFalseMail));
        assertFalse(eventService.createEvent("test", 10, dateBegin, coordinatorFalseMail));
    }

    @Test
    public void correctMailTest(){
        assertNotNull(memory.getCoordinatorByMail(coordinatorMail));
        assertTrue(eventService.createEvent("test", 10, dateBegin, coordinatorMail));
    }
}

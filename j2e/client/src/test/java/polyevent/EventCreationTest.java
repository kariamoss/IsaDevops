package polyevent;

import api.EventApi;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.xml.datatype.DatatypeFactory;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotNull;

@Category(IntegrationTests.class)
public class EventCreationTest {

    private EventApi api;

    @Before
    public void init(){
        api = new EventApi();
    }

    @Test
    public void EventCreationRequestTest() throws Exception{
        GregorianCalendar tmpDate = new GregorianCalendar();
        tmpDate.add(Calendar.HOUR_OF_DAY, 24);

        polyevent.Event e = api.eventCreatorService.createEvent("test",
                100,
                DatatypeFactory.newInstance().newXMLGregorianCalendar(tmpDate),
                "MarcDu06@laposte.fr");

        assertNotNull(e);
    }
}

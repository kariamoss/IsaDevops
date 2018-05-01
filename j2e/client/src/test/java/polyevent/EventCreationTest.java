package polyevent;

import api.EventApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@Category(IntegrationTests.class)
public class EventCreationTest extends TestHelper {

    private EventApi api;
    private Coordinator coordinator;
    private Event event;

    @Before
    public void init(){
        api = new EventApi();
        try {
            coordinator = api.coordinatorService.register("jehan", "milleret", generateEmail(), "passwd");
        } catch (InvalidRequestParametersException_Exception | UserAlreadyExistsException_Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EventCreationRequestTest() throws Exception{
        GregorianCalendar tmpDate = new GregorianCalendar();
        tmpDate.add(Calendar.HOUR_OF_DAY, 24);

        event = api.eventCreatorService.createEvent("test",
                100,
                DatatypeFactory.newInstance().newXMLGregorianCalendar(tmpDate), coordinator);

        assertNotNull(event);
    }
}

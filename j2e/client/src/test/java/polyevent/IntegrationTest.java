package polyevent;

import api.EventApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;


@Category(IntegrationTests.class)
public class IntegrationTest extends TestHelper {

    private EventApi api;

    @Before
    public void init(){
        api = new EventApi();
    }

    @Test
    public void authenticateAndCreateEventTest() throws InvalidRequestParametersException_Exception {
        Client client = new Client();

        String createCoordinator = "createCoordinator jehan milleret " + generateEmail() + " passwd";
        InputStream is = new ByteArrayInputStream(createCoordinator.getBytes(StandardCharsets.UTF_8));
        client.run(is, false, 0);



        String eventName = generateEvent();
        String createEvent ="createEvent " + eventName + " 30\n";
        is = new ByteArrayInputStream(createEvent.getBytes(StandardCharsets.UTF_8));
        client.run(is, false, 0);

        assertNotNull(api.eventCatalogService.getEventWithName(eventName));
    }
}

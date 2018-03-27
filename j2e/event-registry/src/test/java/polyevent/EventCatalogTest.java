package polyevent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

public class EventCatalogTest {

    @EJB
    private IEventCatalog eventCatalog;
    @EJB
    private IEventCatalog eventCatalog2;

    private Database database;
    private Database database2;

    private String lookUpEventName;

    @Before
    public void setUpMocks() {
        // mocks the database entries, and verifies that
        List<Event> events = new ArrayList<>();
        List<RoomType> l = new ArrayList<>();
        l.add(RoomType.MEETING_ROOM);

        lookUpEventName = "Event1";

        events.add(new Event(100, lookUpEventName, l));
        events.add(new Event(10, "Event2", l));

        eventCatalog = new EventCatalog();
        eventCatalog2 = new EventCatalog();

        database = Mockito.mock(Database.class);
        database2 = Mockito.mock(Database.class);
        when(database.getEvents()).thenReturn(events);
        when(database.findEventByName(notNull(String.class))).thenReturn(events.get(0));
        when(database2.getEvents()).thenReturn(new ArrayList<>());
        when(database2.findEventByName(notNull(String.class))).thenReturn(null);
        ((EventCatalog) eventCatalog).database = database;
        ((EventCatalog) eventCatalog2).database = database2;
    }

    @Test
    public void testGetAllEvents() {
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals("Size of event list should be equal to 2", optionalEvents.get().size(), 2);
        assertEquals(optionalEvents.get().get(0).getName(), lookUpEventName);
    }

    @Test
    public void testFindEventWithName() {
        Optional<Event> optionalEvent = eventCatalog.getEventWithName(lookUpEventName);
        assertTrue(optionalEvent.isPresent());
        assertEquals(optionalEvent.get().getName(), lookUpEventName);
    }

    @Test
    public void testGetAllEventsEmpty() {
        Optional<List<Event>> optionalEvents = eventCatalog2.getAllEvents();
        assertTrue(optionalEvents.isPresent());
        assertEquals(optionalEvents.get().size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindEventWithNameEmpty() {
        Optional<Event> optionalEvent = eventCatalog2.getEventWithName(lookUpEventName);
        assertFalse(optionalEvent.isPresent());
        Object whatever = optionalEvent.get(); // throws the NoSuchElementException
    }
}

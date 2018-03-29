package webservice.event;

import polyevent.Event;
import polyevent.IEventCatalog;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService
@Stateless
public class EventCatalogService implements IEventCatalogService {

    @EJB public IEventCatalog eventCatalog;

    /**
     * Consumes the IEventCatalog bean to
     * perform a research on all created events and returns them to the client
     *
     * @return an optional containing the list of events retrieved by the IEventCatalog bean,
     * or {@link Optional#empty} if no events were found by the IEventCatalog bean
     */
    @Override
    public List<Event> getAllEvents() {
        Optional<List<Event>> optionalEvents = eventCatalog.getAllEvents();
        return optionalEvents.orElseGet(ArrayList::new);
    }

    /**
     * Consumes the IEventCatalog bean to
     * perform a research on all created events, and returns the first {@link Event}
     * with the given name, or {@link Optional#empty} if no such {@link Event}
     * could be found
     *
     * @param eventName the name of the event to retrieve
     * @return an {@link Optional<Event>} or {@link Optional#empty}
     */
    @Override
    public Event getEventWithName(String eventName) {
        Optional<Event> event = eventCatalog.getEventWithName(eventName);
        return event.orElse(null);
    }
}

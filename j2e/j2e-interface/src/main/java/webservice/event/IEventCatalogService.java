package webservice.event;

import polyevent.entities.Event;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;

@WebService
public interface IEventCatalogService {

    /**
     * Consumes the IEventCatalog bean to
     * perform a research on all created events and returns them to the client
     *
     * @return an optional containing the list of events retrieved by the IEventCatalog bean,
     *         or {@link Optional#empty} if no events were found by the IEventCatalog bean
     */
    @WebMethod
    List<Event> getAllEvents();

    /**
     * Consumes the IEventCatalog bean to
     * perform a research on all created events, and returns the first {@link Event}
     * with the given name, or {@link Optional#empty} if no such {@link Event}
     * could be found
     *
     * @param eventName the name of the event to retrieve
     * @return an {@link Optional<Event>} or {@link Optional#empty}
     */
    @WebMethod
    Event getEventWithName(String eventName);
}

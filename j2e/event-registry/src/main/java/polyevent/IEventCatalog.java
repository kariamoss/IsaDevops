package polyevent;

import polyevent.entities.Event;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.interceptors.validation.standard.InterceptorStringVerifier;

import javax.ejb.Local;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;

@Local
public interface IEventCatalog {
    /**
     * Looks up in the event database and returns all the entries
     * found in the table
     * @return an optional containing the list of events in the database,
     *         or {@link Optional#empty} if no events were found in the database
     */
    Optional<List<Event>> getAllEvents();

    /**
     * Looks up in the database and returns the first {@link Event}
     * with the given name, or {@link Optional#empty} if no such {@link Event}
     * could be found
     *
     *
     * @param eventName the name of the event to retrieve
     * @return an {@link Optional<Event>} or {@link Optional#empty}
     */
    @Interceptors({InterceptorStringVerifier.class})
    Optional<Event> getEventWithName(String eventName) throws InvalidRequestParametersException;
}

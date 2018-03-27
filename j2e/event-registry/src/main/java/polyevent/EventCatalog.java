package polyevent;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

public class EventCatalog implements IEventCatalog {

    @EJB protected Database database;

    /**
     * Looks up in the event database and returns all the entries
     * found in the table
     *
     * @return an optional containing the list of events in the database,
     * or {@link Optional#empty} if no events were found in the database
     */
    @Override
    public Optional<List<Event>> getAllEvents() {
        // database.getEvents() will be an empty List<Event>
        // if no events have been created yet
        return Optional.of(database.getEvents());
    }

    /**
     * Looks up in the database and returns the first {@link Event}
     * with the given name, or {@link Optional#empty} if no such {@link Event}
     * could be found
     *
     * @param eventName the name of the event to retrieve
     * @return an {@link Optional<Event>} or {@link Optional#empty}
     */
    @Override
    public Optional<Event> getEventWithName(String eventName) {
        // We use ofNullable because an event with such a name may not yet exist in the database
        // this is not a situation where the back-end should crash with a NPE
        // instead, it should output this information to the client (no event with such name)
        return Optional.ofNullable(database.findEventByName(eventName));
    }
}

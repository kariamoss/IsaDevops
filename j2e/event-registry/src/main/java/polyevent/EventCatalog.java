package polyevent;

import polyevent.entities.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EventCatalog implements IEventCatalog {

    @PersistenceContext private EntityManager entityManager;

    private Logger l = Logger.getLogger(EventCatalog.class.getName());


    /**
     * Looks up in the event database and returns all the entries
     * found in the table
     *
     * @return an optional containing the list of events in the database,
     * or {@link Optional#empty} if no events were found in the database
     */
    @Override
    public Optional<List<Event>> getAllEvents() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
        Root<Event> root =  criteria.from(Event.class);
        criteria.select(root);
        TypedQuery<Event> query = entityManager.createQuery(criteria);
        try {
            // No need to use ofNullable because an event with such a name may not yet exist
            // in the database, but first of all,
            // this is not a situation where the back-end should crash with a NPE,
            // second the JPA framework has built-in exceptions to handle such case
            return Optional.of(query.getResultList());
        } catch (NoResultException nre){
            l.log(Level.FINEST, "No events found in the database", nre);
            // database.getEvents() will be an empty List<Event>
            // if no events have been created yet
            return Optional.empty();
        }
    }

    /**
     * Looks up in the database and returns the first {@link Event}
     * with the given name, or {@link Optional#empty} if no such {@link Event}
     * could be found
     *
     * @implNote The parameter constraint validation is done in the bean interface
     *           {@link IEventCatalog#getEventWithName(String)}
     *
     * @param eventName the name of the event to retrieve
     * @return an {@link Optional<Event>} or {@link Optional#empty}
     */
    @Override
    public Optional<Event> getEventWithName(String eventName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
        Root<Event> root =  criteria.from(Event.class);
        criteria.select(root).where(builder.equal(root.get("name"), eventName));
        TypedQuery<Event> query = entityManager.createQuery(criteria);
        try {
            // No need to use ofNullable because an event with such a name may not yet exist
            // in the database, but first of all,
            // this is not a situation where the back-end should crash with a NPE,
            // second the JPA framework has built-in exceptions to handle such case
            Optional<List<Event>> optionalEvents = Optional.of(query.getResultList());
            if (optionalEvents.get().size() == 0) {
                return Optional.empty();
            }
            else {
                return Optional.of(optionalEvents.get().get(0));
            }
        } catch (NoResultException nre){
            l.log(Level.FINEST, "No result for ["+eventName+"]", nre);
            return Optional.empty();
        }
    }
}

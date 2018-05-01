package polyevent;

import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class EventCreator implements IEventCreator {

    @EJB
    protected IEventOrganizer eventOrganizer;

    @PersistenceContext
    protected EntityManager entityManager;

    private Logger l = Logger.getLogger(EventCreator.class.getName());

    @Override
    public Event registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {

        l.log(Level.INFO, "Received request for the event creation");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 2);

        Coordinator c = entityManager.find(Coordinator.class, coordinator.getId());
        Event event = new Event(c, date.getTime(), cal.getTime(), participantNumber, name);
        c.getEventsCreated().add(event);

        return eventOrganizer.bookRoom(event);
    }

    @Override
    public boolean cancelEvent(Coordinator coordinator, Event event) throws DataIntegrityException {
        if (!coordinator.getEventsCreated().remove(event)) {
            l.log(Level.SEVERE, "Tried to delete event from coordinator that doesn't exist in the database");
            throw new DataIntegrityException("The given event doesn't exist for this coordinator : " + event);
        }
        event.setCoordinator(null);
        entityManager.remove(event);

        return true;
    }
}

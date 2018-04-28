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

        if (!areParametersValid(name, participantNumber, date, coordinator)) {
            l.log(Level.SEVERE, "Received invalid parameter(s) for event creation");
            throw new InvalidRequestParametersException("Parameters of the request are invalid");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 12);

        Event event = new Event(coordinator, date.getTime(), cal.getTime(), participantNumber, name);
        coordinator.addEvent(event);

        entityManager.merge(coordinator);



        return eventOrganizer.bookRoom(event);
    }

    @Override
    public boolean cancelEvent(Coordinator coordinator, Event event) throws DataIntegrityException {
        if (!coordinator.removeEvent(event)) {
            l.log(Level.SEVERE, "Tried to delete event from coordinator that doesn't exist in the database");
            throw new DataIntegrityException("The given event doesn't exist for this coordinator : " + event);
        }
        event.setCoordinator(null);
        entityManager.remove(event);

        return true;
    }

    /**
     * Checks that all the parameters received for an {@link Event} creation are valid
     * and returns true if they are, or false if they're not
     * @param name the name of the {@link Event}, as a {@link String}
     * @param participantNumber the estimated number of people for the
     *                          {@link Event} as a {@link Integer}
     * @param date the date at which the {@link Event} is scheduled,
     *             as a {@link Calendar}
     * @param coordinator the {@link Coordinator} that created the {@link Event}
     * @return true if all the given parameters are valid, and the event creation shall be processed
     */
    private boolean areParametersValid(String name, int participantNumber, Calendar date, Coordinator coordinator) {
        return FieldsValidator.isStringValid(name)
                && FieldsValidator.isStrictlyPositive(participantNumber)
                && FieldsValidator.dateIsGood(date)
                && FieldsValidator.isObjectNotNull(coordinator);
    }
}

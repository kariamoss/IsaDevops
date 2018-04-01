package polyevent;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class EventCreator implements IEventCreator {

    @EJB protected IEventOrganizer eventOrganizer;
    @EJB protected Database memory;

    private Logger l = Logger.getLogger(EventCreator.class.getName());

    @Override
    public Message registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) {

        l.log(Level.INFO, "Received request for event creation");

        if (!areParametersValid(name, participantNumber, date, coordinator)) {
            return new Message()
                    .withStatus(400)
                    .withStatusText("Parameters of the request are invalid")
                    .withTransmittedObject(new IllegalArgumentException("Parameters of the request are invalid"));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 12);

        Event event = new Event(coordinator, date.getTime(), cal.getTime(), participantNumber, name);
        memory.addEvent(event);

        return eventOrganizer.bookRoom(event);
    }

    @Override
    public boolean cancelEvent(Event event) {
        return memory.deleteEvent(event);
    }

    /**
     * Returns true if the given name is non null and
     * is not empty
     * @param name the name of the {@link Event}
     * @return true if the name semantically of the {@link Event} is valid
     */
    private boolean nameIsGood(String name) {
        return name != null && !name.trim().equals("");
    }

    /**
     * Returns true if the number of participant is strictly positive
     * @param participantNumber the number of people in the {@link Event}
     * @return true if the number of participant is valid
     */
    private boolean participantNumberIsGood(int participantNumber) {
        return participantNumber > 0;
    }

    /**
     * Checks if the given date is a date in the future,
     * and returns true if it is
     * @param date the date to check
     * @return true if the provided start date for the {@link Event} is valid
     */
    private boolean dateIsGood(Calendar date) {
        return date.after(Calendar.getInstance());
    }

    /**
     * Checks if the given {@link Coordinator} is a valid coordinator,
     * i.e., if he's not null, and returns true if he is not
     * @param coordinator the {@link Coordinator} to check
     * @return true if the {@link Coordinator} is valid
     */
    private boolean coordinatorIsGood(Coordinator coordinator) {
        return coordinator != null;
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
        return nameIsGood(name) && participantNumberIsGood(participantNumber) && dateIsGood(date) && coordinatorIsGood(coordinator);
    }
}

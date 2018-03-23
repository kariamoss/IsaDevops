package polyevent;

import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;

/**
 * Hello world!
 *
 */
@Stateless
public class EventCreator implements IEventCreator {

    @EJB private IEventOrganizer eventOrganizer;
    @EJB private transient Database memory;

    @Override
    public boolean registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) {
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
}

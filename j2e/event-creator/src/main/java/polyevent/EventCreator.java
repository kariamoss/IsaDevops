package polyevent;

import javax.ejb.Stateless;
import java.util.Calendar;

/**
 * Hello world!
 *
 */
@Stateless
public class EventCreator implements IEventCreator
{
    @Override
    public boolean registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) {
        return false;
    }

    @Override
    public boolean cancelEvent(Event event) {
        return false;
    }
}

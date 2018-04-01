package polyevent;

import polyevent.communication.Message;

import javax.ejb.Local;
import java.util.Calendar;

@Local
public interface IEventCreator {

    Message registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator);

    boolean cancelEvent(Event event);
}

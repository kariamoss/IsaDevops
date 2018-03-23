package polyevent;



import java.util.Calendar;

import javax.ejb.Local;

@Local
public interface IEventCreator {

    boolean registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator);

    boolean cancelEvent(Event event);
}

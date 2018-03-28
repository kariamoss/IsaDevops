package webservice.event;

import polyevent.Coordinator;
import polyevent.Database;
import polyevent.IEventCreator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Calendar;


@WebService
@Stateless
public class EventService implements IEventService {

    @EJB public IEventCreator eventCreator;
    @EJB public Database memory;

    @Override
    public boolean createEvent(String eventName, int nbParticipant, Calendar date, String coordinatorMail) {
        Coordinator coordinator = memory.getCoordinatorByMail(coordinatorMail);

        return coordinator != null && eventCreator.registerEvent(eventName, nbParticipant, date, coordinator);
    }
}

package webservice;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import polyevent.Coordinator;
import polyevent.Database;
import polyevent.IEventCreator;

import java.util.Calendar;
import java.util.Date;


@WebService
public class EventService implements IEventService {

    @EJB private IEventCreator eventCreator;
    @EJB private transient Database memory;

    @Override
    public boolean createEvent(String eventName, int nbParticipant, XMLGregorianCalendar date, String coordinatorMail) {
        Coordinator coordinator = memory.getCoordinatorByMail(coordinatorMail);

        Calendar dt = date.toGregorianCalendar();
        return coordinator != null && eventCreator.registerEvent(eventName, nbParticipant, dt, coordinator);
    }
}

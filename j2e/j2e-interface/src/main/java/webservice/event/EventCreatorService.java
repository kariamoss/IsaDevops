package webservice.event;

import polyevent.Coordinator;
import polyevent.Database;
import polyevent.IEventCreator;
import polyevent.communication.Message;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Calendar;


@WebService
@Stateless
public class EventCreatorService implements IEventCreatorService {

    @EJB public IEventCreator eventCreator;
    @EJB public Database memory;

    @Override
    public Message createEvent(String eventName, int nbParticipant, Calendar date, String coordinatorMail) {
        Coordinator coordinator = memory.getCoordinatorByMail(coordinatorMail);

        if (coordinator == null)
            return new Message()
                    .withStatus(400)
                    .withStatusText("The coordinator doesn't exist")
                    .withTransmittedObject(new IllegalArgumentException("The coordinator doesn't exist"));

        return  eventCreator.registerEvent(eventName, nbParticipant, date, coordinator);
    }
}

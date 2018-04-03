package webservice.event;

import polyevent.*;

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
    public Event createEvent(String eventName, int nbParticipant, Calendar date, String coordinatorMail) throws InvalidCredentialsException, InvalidRequestParametersException, DatabaseSavingException, InvalidRoomException, RoomNotAvailableException {
        Coordinator coordinator = memory.getCoordinatorByMail(coordinatorMail);

        if (coordinator == null) {
            throw new InvalidCredentialsException("The coordinator doesn't exist");
        }

        return eventCreator.registerEvent(eventName, nbParticipant, date, coordinator);
    }
}

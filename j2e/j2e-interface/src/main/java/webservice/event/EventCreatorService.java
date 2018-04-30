package webservice.event;

import polyevent.ICoordinatorAuthenticator;
import polyevent.IEventCreator;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Calendar;


@WebService
@Stateless
public class EventCreatorService implements IEventCreatorService {

    @EJB
    public IEventCreator eventCreator;

    @EJB
    ICoordinatorAuthenticator coordinatorAuthenticator;

    @Override
    public Event createEvent(String eventName, int nbParticipant, Calendar date, Coordinator coordinator) throws InvalidCredentialsException, InvalidRequestParametersException, DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        // Finds a coordinator with its email
        // be careful when querying on columns different than the ID
        // since the column needs to contain unique values
        // todo delegate this to EventCreator bean ...
        Coordinator c = coordinatorAuthenticator.authenticate(coordinator.getEmail(), coordinator.getPassword());

        if (c == null) {
            throw new InvalidCredentialsException("The coordinator doesn't exist");
        }

        return eventCreator.registerEvent(eventName, nbParticipant, date, coordinator);
    }
}

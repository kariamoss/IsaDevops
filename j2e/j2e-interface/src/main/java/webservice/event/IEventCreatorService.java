package webservice.event;


import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Calendar;

@WebService
public interface IEventCreatorService {

    @WebMethod
    Event createEvent(String eventName, int nbParticipant, Calendar date, Coordinator coordinator) throws InvalidCredentialsException, InvalidRequestParametersException, DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException;
}

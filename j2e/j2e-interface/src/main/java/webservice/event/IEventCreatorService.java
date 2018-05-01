package webservice.event;


import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;
import polyevent.interceptors.validation.coordinator.InterceptorCoordinatorVerifier;

import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Calendar;

@WebService
public interface IEventCreatorService {

    @WebMethod
    @Interceptors({InterceptorCoordinatorVerifier.class})
    Event createEvent(String eventName, int nbParticipant, Calendar date, Coordinator coordinator) throws InvalidCredentialsException, InvalidRequestParametersException, DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException;
}

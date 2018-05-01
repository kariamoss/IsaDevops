package polyevent;

import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.*;
import polyevent.interceptors.validation.coordinator.InterceptorCoordinatorVerifier;
import polyevent.interceptors.validation.standard.InterceptorIntegerVerifier;
import polyevent.interceptors.validation.standard.InterceptorStringVerifier;

import javax.ejb.Local;
import javax.interceptor.Interceptors;
import java.util.Calendar;

@Local
public interface IEventCreator {

    @Interceptors({InterceptorStringVerifier.class, InterceptorIntegerVerifier.class, InterceptorCoordinatorVerifier.class})
    Event registerEvent(String name, int participantNumber, Calendar date, Coordinator coordinator) throws InvalidRequestParametersException, RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException;

    @Interceptors({InterceptorCoordinatorVerifier.class})
    boolean cancelEvent(Coordinator coordinator, Event event) throws DataIntegrityException;
}

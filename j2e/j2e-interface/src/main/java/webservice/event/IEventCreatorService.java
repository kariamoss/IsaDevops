package webservice.event;


import polyevent.Message;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Calendar;

@WebService
public interface IEventCreatorService {

    @WebMethod
    Message createEvent(String eventName, int nbParticipant, Calendar date, String coordinatorMail);
}

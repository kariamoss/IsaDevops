package webservice.event;


import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Calendar;

@WebService
public interface IEventCreatorService {

    @WebMethod
    boolean createEvent(String eventName, int nbParticipant, Calendar date, String coordinatorMail);
}
package webservice;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

@WebService
public interface IEventService {

    @WebMethod
    boolean createEvent(String eventName, int nbParticipant, XMLGregorianCalendar date, String coordinatorMail);
}

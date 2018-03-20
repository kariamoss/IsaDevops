package api;

import webservice.EventServiceService;
import webservice.IEventService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventApi {
    private String url = "/polyevent-backend/EventServiceService";

    public IEventService eventService;

    public EventApi(){
        initEvent();
    }

    private void initEvent(){
        EventServiceService factory = new EventServiceService();
        this.eventService = factory.getEventServicePort();
    }
    //testing purpose
    public static void main(String[] args) throws DatatypeConfigurationException {
        EventApi api = new EventApi();
        EventApi api2 = new EventApi();

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date(2018, 10 , 10, 10, 15));
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);


        System.out.println(api.eventService.createEvent("myEvent", 100, date2, "salut"));
        //TODO Check error (probably due to eventCreator null at EventService.java)
        System.out.println(api.eventService.createEvent("myEvent", 100, date2, "MarcDu06@laposte.fr"));
    }
}

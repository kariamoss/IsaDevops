package api;

import webservice.EventServiceService;
import webservice.IEventService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

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
    public static void main(String[] args) {
        EventApi api = new EventApi();
        EventApi api2 = new EventApi();
        System.out.println(api.eventService.createEvent("salut"));
        System.out.println(api2.eventService.createEvent("salut"));
    }
}

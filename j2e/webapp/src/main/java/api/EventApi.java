package api;

import webservice.EventServiceService;
import webservice.IEventService;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class EventApi {
    String url = "/polyevent-backend/EventServiceService";

    public IEventService eventService;

    public EventApi(){
        initEvent("localhost","8080");
    }

    private void initEvent(String host, String port){
        URL wsdlLocation = EventApi.class.getResource("/EventService.wsdl");
        EventServiceService factory = new EventServiceService(wsdlLocation);
        this.eventService = factory.getEventServicePort();
        String address = "http://" + host + ":" + port + url;
        ((BindingProvider) eventService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
    }
    //testing purpose 
    public static void main(String[] args) {
        EventApi api = new EventApi();
        System.out.println(api.eventService.createEvent("salut"));
    }
}

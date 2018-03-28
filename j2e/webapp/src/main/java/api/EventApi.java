package api;

import polyevent.EventCatalogServiceService;
import polyevent.IEventCatalogService;
import webservice.EventServiceService;
import webservice.IEventService;


public class EventApi {
    private String url = "/polyevent-backend/EventServiceService";

    public IEventService eventCreatorService;
    public IEventCatalogService eventCatalogService;

    public EventApi(){
        initEvent();
    }

    private void initEvent(){
        EventServiceService factory = new EventServiceService();
        this.eventCreatorService = factory.getEventServicePort();

        EventCatalogServiceService catalogFactory = new EventCatalogServiceService();
        this.eventCatalogService = catalogFactory.getEventCatalogServicePort();
    }
}

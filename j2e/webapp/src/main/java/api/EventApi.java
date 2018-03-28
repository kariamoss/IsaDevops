package api;

import webservice.EventServiceService;
import webservice.IEventService;
import webservice.event.EventCatalogServiceService;
import webservice.event.IEventCatalogService;

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

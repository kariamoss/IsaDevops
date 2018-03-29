package api;

import polyevent.EventCatalogServiceService;
import polyevent.EventServiceService;
import polyevent.IEventCatalogService;
import polyevent.IEventService;


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

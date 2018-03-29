package api;

import polyevent.EventCatalogServiceService;
import polyevent.EventCreatorServiceService;
import polyevent.IEventCatalogService;
import polyevent.IEventCreatorService;


public class EventApi {
    private String url = "/polyevent-backend/EventServiceService";

    public IEventCreatorService eventCreatorService;
    public IEventCatalogService eventCatalogService;

    public EventApi(){
        initEvent();
    }

    private void initEvent(){
        EventCreatorServiceService factory = new EventCreatorServiceService();
        this.eventCreatorService = factory.getEventCreatorServicePort();

        EventCatalogServiceService catalogFactory = new EventCatalogServiceService();
        this.eventCatalogService = catalogFactory.getEventCatalogServicePort();
    }
}

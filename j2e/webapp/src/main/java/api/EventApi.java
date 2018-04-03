package api;

import polyevent.*;


public class EventApi {
    private String url = "/polyevent-backend/EventServiceService";

    public IEventCreatorService eventCreatorService;
    public IEventCatalogService eventCatalogService;
    public ICoordinatorService coordinatorService;


    public EventApi(){
        initEvent();
    }

    private void initEvent(){
        EventCreatorServiceService factory = new EventCreatorServiceService();
        this.eventCreatorService = factory.getEventCreatorServicePort();

        EventCatalogServiceService catalogFactory = new EventCatalogServiceService();
        this.eventCatalogService = catalogFactory.getEventCatalogServicePort();

        CoordinatorServiceService coordinatorFactory = new CoordinatorServiceService();
        this.coordinatorService = coordinatorFactory.getCoordinatorServicePort();
    }
}

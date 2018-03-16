package fr.unice.polytech.isa.polyevent.webservice;

public class EventService implements IEventService {

    @Override
    public void createEvent(String name) {
        System.out.println(name);
    }
}

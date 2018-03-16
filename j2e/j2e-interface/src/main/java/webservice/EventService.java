package webservice;

public class EventService implements IEventService {

    @Override
    public void createEvent(String name) {
        System.out.println(name);
    }
}

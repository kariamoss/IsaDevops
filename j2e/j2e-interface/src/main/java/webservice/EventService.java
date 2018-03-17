package webservice;

import javax.jws.WebService;

@WebService
public class EventService implements IEventService {

    @Override
    public void createEvent(String name) {
        System.out.println(name);
    }
}

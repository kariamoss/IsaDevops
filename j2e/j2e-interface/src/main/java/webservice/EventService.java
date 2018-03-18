package webservice;

import javax.jws.WebService;

@WebService
public class EventService implements IEventService {

    @Override
    public String createEvent(String name) {
        return "salut";
    }
}

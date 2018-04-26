package polyevent;

import org.apache.cxf.jaxrs.client.WebClient;
import polyevent.entities.Room;
import polyevent.exceptions.ExternalServiceCommunicationException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgendaAPI {
    private String url;

    private Logger l = Logger.getLogger(AgendaAPI.class.getName());

    public AgendaAPI(String host, String port) {
        this.url = "http://" + host + ":" + port;
    }

    public AgendaAPI() {
        this("localhost", "9090");
    }

    public boolean bookRoom(Room room) throws ExternalServiceCommunicationException {
        try {
            String str = WebClient.create(url).path("/book/" + room.getName()).get(String.class);
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            l.log(Level.SEVERE, "AgendaApi couldn't reach the external service for room booking");
            throw new ExternalServiceCommunicationException("AgendaApi couldn't reach the external service for room booking");
        }
    }
}

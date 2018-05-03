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
        build(host,port);
    }

    public AgendaAPI() {
        String addr = "localhost";
        String port = "9090";

        String envAddr;
        String envPort;

        if((envAddr = System.getenv("AgendaAddr")) != null){
            addr = envAddr;
        }

        if((envPort = System.getenv("AgendaPort")) != null){
            port = envPort;
        }

        build(addr,port);
    }

    private void build(String host,String port){
        this.url = "http://" + host + ":" + port;
    }

    public boolean bookRoom(Room room) {
        try {
            String str = WebClient.create(url).path("/book/" + room.getName()).get(String.class);
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            l.log(Level.SEVERE, "AgendaApi couldn't reach the external service for room booking");
        }
        return false;
    }

    public String getUrl() {
        return url;
    }
}

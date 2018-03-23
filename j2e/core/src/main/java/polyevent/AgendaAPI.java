package polyevent;

import org.apache.cxf.jaxrs.client.WebClient;

public class AgendaAPI {
    private String url;

    public AgendaAPI(String host, String port) {
        this.url = "http://" + host + ":" + port;
    }

    public AgendaAPI() {
        this("localhost", "9090");
    }

    public boolean bookRoom(Room room) {
        String str = WebClient.create(url).path("/book/" + room.getName()).get(String.class);
        return Boolean.parseBoolean(str);
    }
}

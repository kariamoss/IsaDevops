package polyevent;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class Database {

    private List<Coordinator> coordinators;
    private List<Event> events;

    public Database() {
        this.coordinators = Collections.singletonList(
                new Coordinator("Marc", "Jourdes", "MarcDu06@laposte.fr")
        );
        this.events = new ArrayList<>();
    }

    public Coordinator getCoordinatorByMail(String mail){
        for(Coordinator coordinator : coordinators){
            if(coordinator.getEmail().equals(mail)){
                return coordinator;
            }
        }
        return null;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public boolean deleteEvent(Event event) {
        return events.remove(event);
    }

}

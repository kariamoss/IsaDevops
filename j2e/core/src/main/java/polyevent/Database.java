package polyevent;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class Database {

    private List<Coordinator> coordinators;
    private List<Event> events;

    private Logger l = Logger.getLogger(Database.class.getName());

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
        l.log(Level.INFO, "inserting event in db"); //todo : add Event::toString
        events.add(event);
    }

    public boolean deleteEvent(Event event) {
        return events.remove(event);
    }

    public boolean bookRoomsToEvent(Event event, List<Room> rooms) {
        l.log(Level.INFO, "Booking rooms to event");
        if(deleteEvent(event)) {
            event.addRooms(rooms);
            addEvent(event);
            return true;
        }
        return false;
    }

}

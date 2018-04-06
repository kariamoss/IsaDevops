package polyevent;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database containing mock of distant database
 * TODO remove database
 */
@Singleton
public class Database {

    private List<Coordinator> coordinators;
    private List<Event> events;

    private Logger l = Logger.getLogger(Database.class.getName());

    public Database() {
        this.coordinators = new ArrayList<>();
        this.coordinators.add(new Coordinator("Marc", "Jourdes", "MarcDu06@laposte.fr")); // todo remove this line
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

    /**
     * An operation to register a new {@link Coordinator} in the database
     * The newly created {@link Coordinator} is passed as the returned value
     * of this method if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName  the last name for this account
     * @param email     the email of this account, used for further authentication
     * @param password  the password of this account, used for further authentication
     * @return a {@link Coordinator} object containing the result of the registration
     * i.e., the newly created {@link Coordinator},
     * process or an exception if a user with the given email already exists in the database
     */
    public Coordinator registerCoordinator(String firstName, String lastName, String email, String password) throws UserAlreadyExistsException {
        if (getCoordinatorByMail(email) != null) {
            throw new UserAlreadyExistsException("A coordinator with this email already exists in the database");
        }

        Coordinator c = new Coordinator(firstName, lastName, email, password);
        this.coordinators.add(c);

        return c;
    }

    public void addEvent(Event event) {
        l.log(Level.INFO, "inserting event in db : " + event.toString());
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

    /**
     * Gets the events {@link Event} registered in the {@link Database}
     * and returns them as a {@link List<Event>}
     * @return the events registered in the database
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Looks up for a {@link Database} entry
     * in the {@link Event} table with the given {@link Event#name}
     * and returns the first entry that was found
     * @param eventName the name of the event to look up
     * @return an {@link Event} with the given name, or null if no {@link Event}
     *         with this name could be found
     */
    public Event findEventByName(String eventName) {
        for (Event e : events) {
            if (e.getName().equals(eventName)) {
                return e;
            }
        }
        return null;
    }
}

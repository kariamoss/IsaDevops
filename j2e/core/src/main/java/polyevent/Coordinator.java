package polyevent;

import java.io.Serializable;
import java.util.List;

public class Coordinator implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private List<Event> eventsCreated;

    public Coordinator(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public List<Event> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(List<Event> eventsCreated) {
        this.eventsCreated = eventsCreated;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

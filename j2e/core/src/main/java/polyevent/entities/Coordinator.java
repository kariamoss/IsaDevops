package polyevent.entities;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="coordinators")
@XmlRootElement
public class Coordinator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private String password;

    @OneToMany(
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            },
            fetch = FetchType.LAZY,
            mappedBy="coordinator"
            // whenever an element is removed from this collection,
            // it is deleted in the database as well
            //orphanRemoval = true
    )
    private List<Event> eventsCreated;

    public Coordinator() {
        // default constructor for JPA instantiation (unmarshalling)
    }

    public Coordinator(String firstName, String lastName, String email) {
        this(firstName, lastName, email, "");
    }

    public Coordinator(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.eventsCreated = new ArrayList<>();
    }

    private void checkEventCreated(){
        if(this.eventsCreated == null) {
            this.eventsCreated = new ArrayList<>();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public List<Event> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(List<Event> eventsCreated) {
        checkEventCreated();
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addEvent(Event e) {
        checkEventCreated();
        this.eventsCreated.add(e);
    }

    public boolean removeEvent(Event e) {
        checkEventCreated();
        return this.eventsCreated.remove(e);
    }

    @Override
    public String toString() {
        return "Coordinator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Two coordinators are equal if they have the same credentials
     * @param o the object to compare to this very one
     * @return true if this object and o are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinator that = (Coordinator) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}

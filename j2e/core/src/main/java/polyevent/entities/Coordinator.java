package polyevent.entities;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="coordinators")
public class Coordinator implements Serializable {

    @Id
    @GeneratedValue
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

    @OneToMany(mappedBy="coordinator")
    private List<Event> eventsCreated;

    @NotNull
    private String password;

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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

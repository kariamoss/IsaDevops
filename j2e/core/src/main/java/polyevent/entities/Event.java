package polyevent.entities;

import org.apache.bval.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="events")
@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class Event implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @XmlTransient
    private Coordinator coordinator;

    @NotNull
    //@Min(0)
    private int nbPeople;

    @NotNull
    @NotEmpty
    private String name;

    private Date startDate;
    private Date endDate;

    //@ManyToMany
    /*@JoinTable(
            name="events_rooms",
            joinColumns = @JoinColumn(
                    name="event_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="room_id",
                    referencedColumnName = "id"
            )
    )*/
    @Transient
    private List<Room> rooms;

    public Event() {
        // default constructor for JPA instantiation (unmarshalling)
    }

    public Event(int nbPeople, String name) {
        this(null, null, null, nbPeople, name);
    }

    public Event(Coordinator coordinator, int nbPeople, String name) {
        this(coordinator, null, null, nbPeople, name);
    }

    public Event(Coordinator coordinator, Date startDate, Date endDate, int nbPeople, String name) {
        this.coordinator = coordinator;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbPeople = nbPeople;
        this.name = name;
    }

    private void checkRoomCreated(){
        if(this.rooms == null) {
            this.rooms = new ArrayList<>();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRooms(List<Room> rooms) {
        checkRoomCreated();
        this.rooms.addAll(rooms);
    }

    public void addRoom(Room room) {
        checkRoomCreated();
        this.rooms.add(room);
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getNbPeople() {
        return nbPeople;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNbPeople(int nbPeople) {
        this.nbPeople = nbPeople;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "Event{" +
                "coordinator=" + coordinator +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", nbPeople=" + nbPeople +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(coordinator, event.coordinator) &&
                Objects.equals(name, event.name) &&
                Objects.equals(startDate, event.startDate) &&
                Objects.equals(endDate, event.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinator, name, startDate, endDate);
    }
}

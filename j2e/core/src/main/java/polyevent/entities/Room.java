package polyevent.entities;

import org.apache.bval.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="rooms")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int capacity;

    @NotNull
    @NotEmpty
    private String name;

    //@ManyToMany(mappedBy = "rooms")
    @Transient
    private List<Event> events;

    public Room() {
        // default constructor for JPA instantiation (unmarshalling)
    }

    public Room(RoomType roomType, int capacity, String name) {
        this.events = new ArrayList<>();
        this.roomType = roomType;
        this.capacity = capacity;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEvent(Event e) {
        if(this.events == null){
            this.events = new ArrayList<>();
        }
        this.events.add(e);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomType=" + roomType +
                ", capacity=" + capacity +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * A room is equal to another room if both names are equal
     * @param o the room to compare with this very one
     * @return true if this object and o are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Needed by JAXB to give a value to the pointer on Coordinator
     * Since the getter is annotated with @XmlTransient, it will by null
     * after the unmarshalling of this object
     * This callback is called by JAXB to give a value instead of null
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        //this.events = (ArrayList<Event>) parent;
    }
}

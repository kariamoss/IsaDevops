package polyevent;

import org.apache.bval.constraints.NotEmpty;
import polyevent.validation.EventValidationInterceptor;
import polyevent.validation.custom.Positive;

import javax.interceptor.Interceptors;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private Coordinator coordinator;
    private Date startDate;
    private Date endDate;
    private int nbPeople;
    private String name;
    private List<Room> rooms;
    private List<RoomType> desiredRoomTypes;

    @Interceptors(EventValidationInterceptor.class)
    public Event(
            @Positive(message = "Number of people at the event can't be zero or negative") int nbPeople,
            @NotEmpty(message =  "Name of the event cannot be empty") String name,
            List<RoomType> roomTypes) {
        this.nbPeople = nbPeople;
        this.name = name;
        this.desiredRoomTypes = roomTypes;
        this.rooms = new ArrayList<>();
    }

    public Event(
            @NotNull(message = "The coordinator couldn't be identified") Coordinator coordinator,
            Date startDate,
            Date endDate,
            @Positive(message = "Number of people at the event can't be zero or negative") int nbPeople,
            @NotEmpty(message =  "Name of the event cannot be empty") String name) {
        this.coordinator = coordinator;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbPeople = nbPeople;
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRooms(List<Room> rooms) {
        this.rooms.addAll(rooms);
    }

    public void addRoom(Room room) {
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
                ", desiredRoomTypes=" + desiredRoomTypes +
                '}';
    }
}

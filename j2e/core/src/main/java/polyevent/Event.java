package polyevent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {
    private Coordinator coordinator;
    private Date startDate;
    private Date endDate;
    private int nbPeople;
    private String name;
    private List<Room> rooms;
    private List<RoomType> desiredRoomTypes;

    public Event(int nbPeople, String name, List<RoomType> roomTypes) {
        this.nbPeople = nbPeople;
        this.name = name;
        this.desiredRoomTypes = roomTypes;
        this.rooms = new ArrayList<>();
    }

    public Event(Coordinator coordinator, Date startDate, Date endDate, int nbPeople, String name) {
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

package polyevent;

import java.io.Serializable;

public class Room implements Serializable {
    private RoomType roomType;
    private int capacity;
    private String name;

    public Room() {
        // default constructor for JPA instantiation (unmarshalling)
    }

    public Room(RoomType roomType, int capacity, String name) {
        this.roomType = roomType;
        this.capacity = capacity;
        this.name = name;
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
}

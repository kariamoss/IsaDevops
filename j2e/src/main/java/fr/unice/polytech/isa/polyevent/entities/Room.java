package fr.unice.polytech.isa.polyevent.entities;

public class Room {
    private RoomType roomType;
    private int capacity;
    private String name;

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
}

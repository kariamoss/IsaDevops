package polyevent.dto;


import polyevent.Room;

/**
 * POJO to manipulate J2E entities in the client
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class RoomDTO {
    private Room room;

    public RoomDTO(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        if (room != null) {
            return "Room{" +
                    "name=" + room.getName() +
                    ", capacity=" + room.getCapacity() +
                    '}';

        }
        return "";
    }
}

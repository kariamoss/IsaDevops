package polyevent.dto;

import polyevent.Event;
import polyevent.Room;

/**
 * POJO to manipulate J2E entities in the client
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class EventDTO {
    private Event event;

    public EventDTO(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        if (event != null) {
            StringBuilder sb = new StringBuilder();

            for (Room r : event.getRoom()) {
                sb.append(new RoomDTO(r).toString());
                sb.append(", ");
            }

            return "Event{" +
                    "name=" + event.getName() +
                    "nbPeople=" + event.getNbPeople() +
                    ", rooms=" + sb.toString() +
                    '}';
        }
        return "";
    }
}

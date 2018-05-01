package polyevent.dto;

import polyevent.Coordinator;
import polyevent.Event;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class CoordinatorDTO {
    private Coordinator coordinator;

    public CoordinatorDTO(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public String toString() {
        if (coordinator != null) {
            StringBuilder sb = new StringBuilder();

            for (Event e : coordinator.getEventsCreated()) {
                sb.append(new EventDTO(e).toString());
                sb.append(", ");
            }

            return "CoordinatorDTO{" +
                    "first name=" + coordinator.getFirstName() +
                    ", last name=" + coordinator.getLastName() +
                    ", events=" + sb.toString() +
                    '}';
        }
        return "";
    }
}

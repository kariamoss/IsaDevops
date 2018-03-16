package fr.unice.polytech.isa.polyevent;


import fr.unice.polytech.isa.polyevent.entities.Event;
import fr.unice.polytech.isa.polyevent.entities.Room;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    boolean book(List<Room> rooms, Event event);

    boolean cancelRoomBooking(List<Room> rooms, Event event);
}

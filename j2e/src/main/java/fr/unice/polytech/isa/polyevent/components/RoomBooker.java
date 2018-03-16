package fr.unice.polytech.isa.polyevent.components;

import fr.unice.polytech.isa.polyevent.IRoomBooker;
import fr.unice.polytech.isa.polyevent.entities.Event;
import fr.unice.polytech.isa.polyevent.entities.Room;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RoomBooker implements IRoomBooker {
    @Override
    public boolean book(List<Room> rooms, Event event) {
        return false;
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

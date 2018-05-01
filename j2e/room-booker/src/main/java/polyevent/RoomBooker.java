package polyevent;


import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue"),})
public class RoomBooker implements MessageListener {

    @PersistenceContext
    private EntityManager entityManager;

    protected AgendaAPI api;
    private Logger l = Logger.getLogger(RoomBooker.class.getName());

    public RoomBooker() {
        api = new AgendaAPI();
    }


    public Event book(List<Room> rooms, Event event) throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException, ExternalServiceCommunicationException {

        l.log(Level.INFO, "Received request for room booking");

        if (rooms.isEmpty()) {
            l.log(Level.SEVERE, "Can't book an empty list of rooms");
            throw new InvalidRoomException("The list of desired rooms cannot be empty");
        }

        for (Room r : rooms) {
            if (!api.bookRoom(r)) {
                l.log(Level.SEVERE, "The reservation for the given room was not possible since it's already booked : " + r);
                throw new RoomNotAvailableException("One or more rooms are not available (they have already been booked)");
            }
        }

        if (!bindRoomsToEvent(event, rooms)) {
            throw new DatabaseSavingException("Internal error while saving the rooms for the event in the database");
        }
        return event;
    }

    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }

    private boolean bindRoomsToEvent(Event event, List<Room> rooms) {
        event.getRooms().addAll(rooms);
        for (Room r : rooms) {
            r.getEvents().add(event);
        }

        return true;
    }

    @Override
    public void onMessage(Message message) {
        try {
            l.log(Level.INFO, "message received");
            BookingWrapper wrapper = (BookingWrapper) ((ObjectMessage) message).getObject();
            book(wrapper.getRooms(), wrapper.getEvent());
        } catch (Exception e) {
            l.log(Level.WARNING, "request for booking couldn't not be made");
            l.log(Level.WARNING, e.getMessage());

        }
    }
}

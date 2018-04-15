package polyevent;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
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

    @EJB protected Database memory;
    protected AgendaAPI api;
    private Logger l = Logger.getLogger(RoomBooker.class.getName());

    public RoomBooker() {
        api = new AgendaAPI();
    }

    public Event book(List<Room> rooms, Event event) throws RoomNotAvailableException, InvalidRoomException, DatabaseSavingException {

        l.log(Level.INFO, "Received request for room booking");

        if(rooms.isEmpty()) {
            l.log(Level.SEVERE, "Can't book an empty list of rooms");
            throw new InvalidRoomException("The list of desired rooms cannot be empty");
        }

        for(Room r : rooms) {
            if (!api.bookRoom(r)) {
                throw new RoomNotAvailableException("One or more rooms are not available (they have already been booked)");
            }
        }

        if(!memory.bookRoomsToEvent(event, rooms)) {
            throw new DatabaseSavingException("Internal error while saving the rooms for the event in the database");
        }

        return event;
    }

    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }


    @Override
    public void onMessage(Message message) {
        try{
            l.log(Level.INFO, "message received");
            BookingWrapper wrapper = (BookingWrapper) ((ObjectMessage) message).getObject();
            book(wrapper.getRooms(),wrapper.getEvent());
        }catch (Exception e) {
            l.log(Level.WARNING, "request for booking couldn't not be made");
            l.log(Level.WARNING, e.getMessage());

        }
    }
}

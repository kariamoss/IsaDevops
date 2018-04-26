package polyevent;


import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.entities.RoomType;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    private Logger l = Logger.getLogger(EventOrganizer.class.getName());





    @Override
    public Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {

        l.log(Level.INFO, "Received request for room booking for event");

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(RoomType.MEETING_ROOM, 100, (new Random()).nextBoolean() ? "E+100" : "E+101"));

        sendToEdt(rooms,event);

        return event;
    }

    @Resource(name = "RoomBooker") private Queue bookingQueue;
    @Resource private ConnectionFactory connectionFactory;


    public void sendToEdt(List<Room> rooms,Event event){
        BookingWrapper wrapper = new BookingWrapper(event,rooms);
        try {
            l.log(Level.INFO, "sending msg");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer booker = session.createProducer(bookingQueue);
            booker.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            booker.send(session.createObjectMessage(wrapper));
            booker.close();
            session.close();
            connection.close();
            l.log(Level.INFO, "msg sent");

        } catch (JMSException e){
            l.log(Level.WARNING,"unable to book room for event :"+event.getName());
        }
    }
}

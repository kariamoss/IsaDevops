package polyevent;


import polyevent.entities.Event;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    @Resource(name = "BookingReceiver") private Queue bookingQueue;
    @Resource private ConnectionFactory connectionFactory;

    private AbstractBehaviour behaviour;

    public EventOrganizer() {
        this.behaviour = new AbstractBehaviour(){};
    }

    @Override
    public Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {

        return behaviour.bookRoom(event, bookingQueue, connectionFactory);
    }
}

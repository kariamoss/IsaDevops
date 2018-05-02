package polyevent;


import polyevent.entities.Event;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.websocket.OnMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    @Resource(name = "BookingReceiver") private Queue bookingQueue;
    @Resource private ConnectionFactory connectionFactory;

    @EJB private IBillCreator billCreator;

    private Logger l = Logger.getLogger(EventOrganizer.class.getName());


    private BehaviourWithBilling behaviour;

    public EventOrganizer() {
        this.behaviour = new BehaviourWithBilling();
    }

    @Override
    public Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        l.log(Level.INFO, String.valueOf(billCreator==null));

        return behaviour.bookRoomWithBilling(event, bookingQueue, connectionFactory, billCreator);
    }
}
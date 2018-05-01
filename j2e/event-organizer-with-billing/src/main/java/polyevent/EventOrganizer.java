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

/**
 * Hello world!
 *
 */
@Stateless
public class EventOrganizer implements IEventOrganizer {

    @Resource(name = "RoomBooker") private Queue bookingQueue;
    @Resource private ConnectionFactory connectionFactory;

    @EJB private IBillCreator billCreator;

    private BehaviourWithBilling behaviour;

    public EventOrganizer() {
        this.behaviour = new BehaviourWithBilling();
    }

    @Override
    public Event bookRoom(Event event) throws DatabaseSavingException, InvalidRoomException, RoomNotAvailableException, ExternalServiceCommunicationException {
        return behaviour.bookRoomWithBilling(event, bookingQueue, connectionFactory, billCreator);
    }
}
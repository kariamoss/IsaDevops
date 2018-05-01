package polyevent;

import polyevent.entities.Event;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

public class BehaviourWithBilling extends AbstractBehaviour {

    public Event bookRoomWithBilling(Event event, Queue bookingQueue, ConnectionFactory connectionFactory, IBillCreator billCreator) {

        Event e = bookRoom(event, bookingQueue, connectionFactory);

        /* #Theo :
          At the moment we do not handle bill creation errors, because billCreator::createBill always returns true.
          Todo when bill creator is fully implemented : create proper error handling.
         */
        billCreator.createBill(event);

        return e;
    }
}

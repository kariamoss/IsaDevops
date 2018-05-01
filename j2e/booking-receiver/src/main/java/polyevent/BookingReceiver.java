package polyevent;


import javax.ejb.*;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
})
public class BookingReceiver implements MessageListener {

    @EJB
    private IRoomBooker roomBooker;

    private Logger l = Logger.getLogger(BookingReceiver.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            l.log(Level.INFO, "message received");
            BookingWrapper wrapper = (BookingWrapper) ((ObjectMessage) message).getObject();
            this.roomBooker.addBooking(new BookingTask(wrapper.getRooms(),wrapper.getEvent()));

        } catch (Exception e) {
            l.log(Level.WARNING, "can't add booking task to queue");
            l.log(Level.WARNING, e.getMessage());

        }
    }
}

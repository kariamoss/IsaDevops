package polyevent;


import polyevent.entities.Event;
import polyevent.entities.Room;
import polyevent.exceptions.DatabaseSavingException;
import polyevent.exceptions.ExternalServiceCommunicationException;
import polyevent.exceptions.InvalidRoomException;
import polyevent.exceptions.RoomNotAvailableException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;

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
    private TaskQueue queue;
    private Logger l = Logger.getLogger(RoomBooker.class.getName());

    @Resource
    private SessionContext context;
    
    @PostConstruct
    public void init() {
        queue = new TaskQueue(entityManager);
        createTimer();
    }

    @Override
    public void onMessage(Message message) {
        try {
            l.log(Level.INFO, "message received");
            BookingWrapper wrapper = (BookingWrapper) ((ObjectMessage) message).getObject();
            this.queue.addTask(new BookingTask(wrapper.getRooms(),wrapper.getEvent()));
        } catch (Exception e) {
            l.log(Level.WARNING, "can't add booking task to queue");
            l.log(Level.WARNING, e.getMessage());

        }
    }

    @Timeout
    public void timeout(Timer timer) {
        l.log(Level.INFO, "timer goes off");
        this.queue.execTask();
        createTimer();
    }

    private void createTimer(){
        context.getTimerService().createTimer(1000,"timer");
    }
}

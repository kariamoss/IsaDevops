package polyevent;

import polyevent.entities.Event;
import polyevent.entities.Room;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class RoomBooker implements IRoomBooker {
    private TaskQueue queue;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private TimerService timerService;

    @PostConstruct
    public void init(){
        queue = new TaskQueue(entityManager);
    }

    private Logger l = Logger.getLogger(RoomBooker.class.getName());


    @Override
    public void addBooking(BookingTask task) {
        queue.addTask(task);

        if(timerService.getAllTimers().size() == 0){
            createTimer();
        }
    }

    @Timeout
    public void timeout(Timer timer){
        l.log(Level.INFO,"timer off");
        if(this.queue.execTask()){
            createTimer();
        }
    }

    private void createTimer(){
        timerService.createTimer(1000,"timer");
    }




    

    @Resource(name = "BookingReceiver") private Queue bookingQueue;
    @Resource private ConnectionFactory connectionFactory;

    @PreDestroy
    public void cleanUp(){
        try {
            l.log(Level.INFO, "sending msg");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer booker = session.createProducer(bookingQueue);
            booker.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for(BookingTask task : this.queue.getTaskList()) {
                booker.send(session.createObjectMessage(task.getWrapper()));
            }

            booker.close();
            session.close();
            connection.close();
            l.log(Level.INFO, "msg sent");

        } catch (JMSException e){
            l.log(Level.WARNING,"unable to send task to book");
        }

    }


}

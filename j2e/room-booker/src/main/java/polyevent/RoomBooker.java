package polyevent;

import polyevent.entities.Event;
import polyevent.entities.Room;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
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


}

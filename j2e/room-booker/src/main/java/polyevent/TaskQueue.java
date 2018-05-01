package polyevent;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskQueue {
    private Queue<BookingTask> queue;
    private EntityManager entityManager;
    private Logger l = Logger.getLogger(TaskQueue.class.getName());


    public TaskQueue(EntityManager entityManager){
        queue = new ConcurrentLinkedQueue<>();
        this.entityManager = entityManager;
    }

    public boolean execTask(){
        l.log(Level.INFO,"executing tasks");
        BookingTask task;

        if((task=queue.poll()) != null) {

            if (!task.book()) {
                queue.add(task);
                return true;
            } else {
                task.bindRoomsToEvent(entityManager);
                return false;
            }
        }
        l.log(Level.INFO,"queue empty");
        return false;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void addTask(BookingTask task){
        l.log(Level.INFO,"adding task");
        queue.add(task);
    }

    public List<BookingTask> getTaskList(){
        return new ArrayList<>(this.queue);
    }


}

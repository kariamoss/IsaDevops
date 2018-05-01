package polyevent;

import javax.persistence.EntityManager;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueue {
    private Queue<BookingTask> queue;
    private EntityManager entityManager;

    public TaskQueue(EntityManager entityManager){
        queue = new ConcurrentLinkedQueue<>();
        this.entityManager = entityManager;
    }

    public void execTask(){
        BookingTask task = queue.poll();

        if(task==null) return;

        if(!task.book()){
            queue.add(task);
        } else {
            task.bindRoomsToEvent(entityManager);
        }
    }

    public void addTask(BookingTask task){
        queue.add(task);
    }
}

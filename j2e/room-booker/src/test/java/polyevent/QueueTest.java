package polyevent;


import org.junit.Before;
import org.junit.Test;
import polyevent.entities.Event;
import polyevent.entities.Room;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class QueueTest {
    private TaskQueue queue;

    @Before
    public void init(){
        queue = new TaskQueue(null);
    }

    @Test
    public void addingTest(){
        assertTrue(this.queue.isEmpty());
        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event()));
        queue.execTask();
        assertFalse(this.queue.isEmpty());
    }

}

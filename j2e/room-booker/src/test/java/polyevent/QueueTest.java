package polyevent;


import org.junit.Before;
import org.junit.Test;
import polyevent.entities.Event;
import polyevent.entities.Room;
import org.mockito.Mockito;


import javax.persistence.EntityManager;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

public class QueueTest {
    private TaskQueue queue;

    @Before
    public void init(){
        EntityManager manager = Mockito.mock(EntityManager.class);
        when(manager.find(Event.class,String.class)).thenReturn(new Event());
        queue = new TaskQueue(manager);
    }

    @Test
    public void addingTest(){
        assertTrue(this.queue.isEmpty());
        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event(),new AgendaAPI("salut787887.com","10000")));
        queue.execTask();
        assertFalse(this.queue.isEmpty());
    }

    @Test
    public void doubleTaskTest(){
        assertTrue(this.queue.isEmpty());
        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event(),new AgendaAPI("salut787887.com","10000")));
        queue.execTask();
        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event(),new AgendaAPI("salut787887.com","10000")));
        queue.execTask();
        assertEquals(queue.getTaskList().size(),2);
    }

    @Test
    public void checkBoolReturned(){
        AgendaAPI api = Mockito.mock(AgendaAPI.class);
        when(api.bookRoom(notNull(Room.class))).thenReturn(true);

        assertTrue(this.queue.isEmpty());

        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event(),api));
        assertFalse(queue.execTask());
        assertTrue(queue.isEmpty());

        queue.addTask(new BookingTask(Arrays.asList(new Room()),new Event(),api));
        assertFalse(queue.execTask());

        assertTrue(queue.isEmpty());
    }

}

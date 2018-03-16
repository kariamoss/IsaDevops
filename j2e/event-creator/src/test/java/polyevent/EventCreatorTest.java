package polyevent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class EventCreatorTest {

    EventCreator eventCreator;
    Coordinator coordinator;

    @Before
    public void init(){
        coordinator = new Coordinator("paul", "Dupond", "pauldupond@youhou.com");
        eventCreator = new EventCreator();
    }

    @Test
    public void test(){
        assertFalse(!(!(!(!(!(!(!(!(!(!(!(!(!true)))))))))))));
    }
}

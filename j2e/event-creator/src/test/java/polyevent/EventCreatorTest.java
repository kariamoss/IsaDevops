package polyevent;

import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventCreatorTest {

    @EJB EventCreator eventCreator;
    @EJB Coordinator coordinator;
    Calendar startDate;

    @Before
    public void init(){
        coordinator = new Coordinator("paul", "Dupond", "pauldupond@youhou.com");
        eventCreator = new EventCreator();
        startDate = Calendar.getInstance();
    }

    /**
     * Creates a new event with good parameters
     * This test should create the event with no problem
     * and return a valid result
     */
    @Test
    public void goodEventCreation() {
        boolean shouldSucceed = eventCreator.registerEvent("toto", 10, startDate, coordinator);
        assertTrue(shouldSucceed);
    }

    /**
     * Registers a new event with a negative number of people
     * The registration should fail because a negative number of people is impossible
     */
    @Test
    public void eventCreationWithBadPeopleNumber() {
        boolean shouldNotSucceed = eventCreator.registerEvent("toto", -10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with an empty name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNoName() {
        boolean shouldNotSucceed = eventCreator.registerEvent("", 10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with a null name
     * The registration should fail because an event should always have a name
     */
    @Test
    public void eventCreationWithNullName() {
        boolean shouldNotSucceed = eventCreator.registerEvent(null, 10, startDate, coordinator);
        assertFalse(shouldNotSucceed);
    }

    /**
     * Registers a new event with a null coordinator
     * The registration should fail because an event is always associated to a coordinator
     */
    @Test
    public void eventCreationWithNullCoordinator() {
        boolean shouldNotSucceed = eventCreator.registerEvent("toto", 10, startDate, null);
        assertFalse(shouldNotSucceed);
    }
}

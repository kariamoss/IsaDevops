package polyevent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class MessageTest {

    private Message message;

    @Before
    public void setUp() throws Exception {
        this.message = new Message();
    }

    @Test
    public void testIsOk() {
        this.message.withStatus(200);
        assertTrue(this.message.isOk());
    }

    /**
     * Tests that all status code except 200 return true
     * to signal an error code
     */
    @Test
    public void testIsNotOK() {
        for (int i = 0; i < 1000; i++) {
            this.message.withStatus(i);
            if (i != 200)
                assertTrue(this.message.isNotOk());
        }
    }

    @Test
    public void testNullTransmittedObject() {
        this.message.withTransmittedObject(null);
        assertEquals(this.message.getTransmittedObject(), null);
        assertEquals(this.message.getMessage(), "");
    }
}
package polyevent.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault(name = "RoomNotAvailableException")
public class RoomNotAvailableException extends Exception implements Serializable {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public RoomNotAvailableException() {
        super("One or more rooms are not available (they have already been booked)");
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RoomNotAvailableException(String message) {
        super(message);
    }
}

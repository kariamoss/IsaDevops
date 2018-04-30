package polyevent.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

/**
 * Defines an {@link Exception} that informs the user that the data integrity has been lost,
 * i.e., the data that the client sent are in an incoherent state, according to the data
 * that the domain layer has
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault(name = "DataIntegrityException")
public class DataIntegrityException extends Exception implements Serializable {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DataIntegrityException() {
        super("The provided data are invalid according to the database");
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DataIntegrityException(String message) {
        super(message);
    }
}

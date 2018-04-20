package polyevent.exceptions;

import javax.xml.ws.WebFault;

/**
 * Defines an {@link Exception} that informs the user that the data integrity has been lost,
 * i.e., the data that the client sent are in an incoherent state, according to the data
 * that the domain layer has
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault
public class DataIntegrityException extends Exception {
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

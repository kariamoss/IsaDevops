package polyevent.exceptions;

import javax.xml.ws.WebFault;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault(name = "InvalidRequestParametersException")
public class InvalidRequestParametersException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidRequestParametersException(String message) {
        super(message);
    }
}

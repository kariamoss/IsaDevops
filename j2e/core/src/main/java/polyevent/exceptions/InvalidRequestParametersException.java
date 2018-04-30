package polyevent.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault(name = "InvalidRequestParametersException")
public class InvalidRequestParametersException extends Exception implements Serializable {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public InvalidRequestParametersException() {
        super("One or more parameters of the request are invalid!");
    }

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

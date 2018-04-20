package polyevent.exceptions;

import javax.xml.ws.WebFault;

/**
 * Exception that describes a communication problem with the external service
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault
public class ExternalServiceCommunicationException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ExternalServiceCommunicationException(String message) {
        super(message);
    }
}

package polyevent.exceptions;

import javax.xml.ws.WebFault;
import java.io.Serializable;

/**
 * An exception that is thrown when an user is created with
 * credentials that already have been used for another user registration
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@WebFault(name = "UserAlreadyExistsException")
public class UserAlreadyExistsException extends Exception implements Serializable {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UserAlreadyExistsException() {
        super("A user with the given email already exists in the database !");
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

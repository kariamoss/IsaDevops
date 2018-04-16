package polyevent;

import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.Local;

/**
 * A functional interface that exposes operations to register
 * a new {@link polyevent.entities.Coordinator}
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@Local
public interface ICoordinatorRegistrator {

    /**
     * An operation to register a new {@link polyevent.entities.Coordinator} in the database
     * The newly created {@link polyevent.entities.Coordinator} is passed as the return value
     * if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName  the last name for this account
     * @param email     the email of this account, used for further authentication
     * @param password  the password of this account, used for further authentication
     * @return a {@link polyevent.entities.Coordinator} object containing the result of the registration
     * process
     */
    Coordinator register(String firstName, String lastName, String email, String password) throws InvalidRequestParametersException, UserAlreadyExistsException;
}

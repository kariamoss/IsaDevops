package polyevent;

import javax.ejb.Local;

/**
 * A functional interface that exposes operations to register
 * a new {@link Coordinator}
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@Local
public interface ICoordinatorRegistrator {

    /**
     * An operation to register a new {@link Coordinator} in the database
     * The newly created {@link Coordinator} is passed through the
     * {@link Message} if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName the last name for this account
     * @param email the email of this account, used for further authentication
     * @param password the password of this account, used for further authentication
     * @return a {@link Message} object containing the result of the registration
     *         process
     */
    Message register(String firstName, String lastName, String email, String password);
}
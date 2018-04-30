package polyevent;

import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;
import polyevent.interceptors.validation.email.InterceptorEmailVerifier;
import polyevent.interceptors.validation.standard.InterceptorStringVerifier;

import javax.ejb.Local;
import javax.interceptor.Interceptors;

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
     * The newly created {@link Coordinator} is passed as the return value
     * if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName  the last name for this account
     * @param email     the email of this account, used for further authentication
     * @param password  the password of this account, used for further authentication
     * @return a {@link Coordinator} object containing the result of the registration
     * process
     */
    @Interceptors({InterceptorStringVerifier.class, InterceptorEmailVerifier.class})
    Coordinator register(String firstName, String lastName, String email, String password) throws InvalidRequestParametersException, UserAlreadyExistsException;
}

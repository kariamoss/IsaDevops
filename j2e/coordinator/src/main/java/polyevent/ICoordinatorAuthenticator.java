package polyevent;

import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.interceptors.validation.email.InterceptorEmailVerifier;
import polyevent.interceptors.validation.standard.InterceptorStringVerifier;

import javax.ejb.Local;
import javax.interceptor.Interceptors;

/**
 * A functional interface that exposes operations to authenticate
 * an existing {@link Coordinator}
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@Local
public interface ICoordinatorAuthenticator {

    /**
     * Authenticates the {@link Coordinator}
     * with the given credentials, and returns the result of the
     * operation trial
     *
     * If the authentication was successful, the corresponding
     * {@link Coordinator} object is passed as the result of the
     * request, otherwise, an {@link Exception}
     * should be returned to indicate the problem that occurred while authenticating
     * the {@link Coordinator}
     *
     * @param email the email of the {@link Coordinator} account
     * @param password the password of the {@link Coordinator} account
     * @return a {@link Coordinator} or an {@link Exception} depending on the
     *         result of the authentication trial
     */
    @Interceptors({InterceptorStringVerifier.class, InterceptorEmailVerifier.class})
    Coordinator authenticate(String email, String password) throws InvalidCredentialsException, InvalidRequestParametersException;
}

package webservice.event;

import polyevent.ICoordinatorAuthenticator;
import polyevent.ICoordinatorRegistrator;
import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless
public class CoordinatorService implements ICoordinatorService {

    @EJB public ICoordinatorRegistrator coordinatorRegistrator;
    @EJB public ICoordinatorAuthenticator coordinatorAuthenticator;

    /**
     * Consume the ICoordinatorRegistrator bean to register a new {@link Coordinator}
     * The newly created {@link Coordinator} is passed through the
     * returned value if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * that should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName the last name for this account
     * @param email the email of this account, used for further authentication
     * @param password the password of this account, used for further authentication
     * @return a {@link Coordinator} object containing the result of the registration
     *         process
     */
    @Override
    public Coordinator register(String firstName, String lastName, String email, String password) throws InvalidRequestParametersException, UserAlreadyExistsException  {
        return coordinatorRegistrator.register(firstName, lastName, email, password);
    }

    /**
     * Consume the ICoordinatorAuthenticator bean to Authenticates the {@link Coordinator}
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
    @Override
    public Coordinator authenticate(String email, String password) throws InvalidCredentialsException, InvalidRequestParametersException{
        return coordinatorAuthenticator.authenticate(email, password);
    }
}

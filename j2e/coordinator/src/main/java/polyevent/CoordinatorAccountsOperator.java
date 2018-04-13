package polyevent;

import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * The implementation for several {@link Coordinator} accounts operations
 *
 * @see ICoordinatorRegistrator
 * @see ICoordinatorAuthenticator
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@Stateless
public class CoordinatorAccountsOperator implements ICoordinatorRegistrator, ICoordinatorAuthenticator {

    @EJB private Database database;

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
    @Override
    public Coordinator register(String firstName, String lastName, String email, String password) throws InvalidRequestParametersException, UserAlreadyExistsException {
        if (!areRegistrationInformationValid(firstName, lastName, email, password)) {
            throw new InvalidRequestParametersException("Parameters for the registration of a new Coordinator are not valid");
        }
        // todo the password should be encrypted and salted before being inserted in the database
        return database.registerCoordinator(firstName, lastName, email, password);
    }

    /**
     * Authenticates the {@link Coordinator}
     * with the given credentials, and returns the result of the
     * operation trial
     * <p>
     * If the authentication was successful, the corresponding
     * {@link Coordinator} object is passed as the result of the
     * request, otherwise, an {@link Exception}
     * should be returned to indicate the problem that occurred while authenticating
     * the {@link Coordinator}
     *
     * @param email the email of the {@link Coordinator} account
     * @param password the password of the {@link Coordinator} account
     * @return a {@link Coordinator} or an {@link Exception} depending on the
     * result of the authentication trial
     */
    @Override
    public Coordinator authenticate(String email, String password) throws InvalidCredentialsException, InvalidRequestParametersException {
        if (!areLoginInformationValid(email, password)) {
            throw new InvalidRequestParametersException("Parameters for coordinator authentication are invalid");
        }

        Coordinator c = database.getCoordinatorByMail(email);
        if (c == null) {
            // we don't inform the user that the email is invalid because
            // we don't want to give information to a potential hacker that the email is wrong
            throw new InvalidCredentialsException("Invalid credentials for login");
        }
        else if (!c.getPassword().equals(password)) {
            // we don't inform the user that the password (only) is invalid because
            // we don't want to give information to a potential hacker that the email he used
            // was right, and only the password was wrong
            throw new InvalidCredentialsException("Invalid credentials for login");
        }
        // clears the password to avoid hacker to leak the password from the object at runtime
        c.setPassword("");
        return c;
    }

    /**
     * Returns true if the credentials of the {@link Coordinator}
     * are valid ones
     *
     * @param email the email to validate
     * @param password the password to validate (as a String)
     * @return true if the given credentials are semantically valid,
     *         false otherwise
     */
    private boolean areLoginInformationValid(String email, String password) {
        return FieldsValidator.isStringValid(password)
                && FieldsValidator.isValidEmail(email);
    }

    /**
     * Returns true if the parameters for the registration of
     * a new {@link Coordinator} are semantically valid
     * @param firstName the first name of the new {@link Coordinator}
     * @param lastName the last name of the new {@link Coordinator}
     * @param email the email of the new {@link Coordinator}
     * @param password the password of the new {@link Coordinator}
     * @return true if the given parameters are semantically valid
     */
    private boolean areRegistrationInformationValid(String firstName, String lastName, String email, String password) {
        return FieldsValidator.isStringValid(firstName)
                && FieldsValidator.isStringValid(lastName)
                && areLoginInformationValid(email, password);
    }
}

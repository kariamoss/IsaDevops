package polyevent;

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
     * The newly created {@link Coordinator} is passed through the
     * {@link Message} if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName  the last name for this account
     * @param email     the email of this account, used for further authentication
     * @param password  the password of this account, used for further authentication
     * @return a {@link Message} object containing the result of the registration
     * process
     */
    @Override
    public Message register(String firstName, String lastName, String email, String password) {
        if (!areRegistrationInformationValid(firstName, lastName, email, password)) {
            return new Message()
                    .withStatus(400)
                    .withStatusText("Parameters for the registration of a new Coordinator are not valid")
                    .withTransmittedObject(new IllegalArgumentException("Parameters for the registration of a new Coordinator are not valid"));
        }

        return null;
    }

    /**
     * Authenticates the {@link Coordinator}
     * with the given credentials, and returns the result of the
     * operation trial
     * <p>
     * If the authentication was successful, the corresponding
     * {@link Coordinator} object is passed as the result of the
     * returned {@link Message}, otherwise, an {@link Exception}
     * should be returned to indicate the problem that occurred while authenticating
     * the {@link Coordinator}
     *
     * @param email the email of the {@link Coordinator} account
     * @param password the password of the {@link Coordinator} account
     * @return a {@link Coordinator} or an {@link Exception} depending on the
     * result of the authentication trial
     */
    @Override
    public Message authenticate(String email, String password) {
        if (!areLoginInformationValid(email, password)) {
            return new Message()
                    .withStatus(400)
                    .withStatusText("Parameters for coordinator authentication are invalid")
                    .withTransmittedObject(new IllegalArgumentException("Parameters for coordinator authentication are invalid"));
        }

        return null;
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

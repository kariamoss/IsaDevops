package polyevent;

import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Logger l = Logger.getLogger(CoordinatorAccountsOperator.class.getName());

    @PersistenceContext private EntityManager entityManager;

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

        if (findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("A user with the given email already exists in the database !");
        }

        // todo the password should be encrypted and salted before being inserted in the database
        Coordinator c = new Coordinator(firstName, lastName, email, password);
        entityManager.persist(c);
        return c;
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

        Optional<Coordinator> c = findByEmail(email);
        if (!c.isPresent()) {
            // we don't inform the user that the email is invalid because
            // we don't want to give information to a potential hacker that the email is wrong
            throw new InvalidCredentialsException("Invalid credentials for login");
        }
        else {
            Coordinator coordinator = c.get();
            if (!coordinator.getPassword().equals(password)) {
                // we don't inform the user that the password (only) is invalid because
                // we don't want to give information to a potential hacker that the email he used
                // was right, and only the password was wrong
                throw new InvalidCredentialsException("Invalid credentials for login");
            }
            // clears the password to avoid hacker to leak the password from the object at runtime
            //todo, call this to avoid transmitting cleartext password on the network : coordinator.setPassword("");
            return coordinator;
        }
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

    /**
     * Looks up in the database to find the {@link Coordinator} with the
     * given email, and returns an {@link Optional<Coordinator>} if such a user
     * exists, or {@link Optional#empty()} if not
     * @param email the email of the {@link Coordinator} to find in the database
     * @return an {@link Optional<Coordinator>} if such a user
     *         exists, or {@link Optional#empty()} if not
     */
    private Optional<Coordinator> findByEmail(String email) throws InvalidRequestParametersException {
        if (!isEmailValid(email)) {
            throw new InvalidRequestParametersException("Cannot find a coordinator with a null or empty email : " + email);
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coordinator> criteria = builder.createQuery(Coordinator.class);
        Root<Coordinator> root =  criteria.from(Coordinator.class);
        criteria.select(root).where(builder.equal(root.get("email"), email));
        TypedQuery<Coordinator> query = entityManager.createQuery(criteria);
        try {
            // No need to use ofNullable because an event with such a name may not yet exist
            // in the database, but first of all,
            // this is not a situation where the back-end should crash with a NPE,
            // second the JPA framework has built-in exceptions to handle such case
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            l.log(Level.FINEST, "No result for ["+email+"]", nre);
            return Optional.empty();
        }
    }

    private boolean isEmailValid(String email) {
        return  FieldsValidator.isStringValid(email)
                && FieldsValidator.isValidEmail(email);
    }
}

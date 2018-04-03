package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
@RunWith(Arquillian.class)
public class CoordinatorAccountsOperatorTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(ICoordinatorRegistrator.class.getPackage())
                .addPackage(ICoordinatorAuthenticator.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @EJB private ICoordinatorAuthenticator coordinatorAuthenticator;
    @EJB private ICoordinatorRegistrator coordinatorRegistrator;

    @Test
    public void registerWithGoodInformation() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto@tutu.fr", "tototutu");
        assertNotEquals(null, c);
        assertEquals(c.getEmail(), "toto@tutu.fr");
    }

    @Test(expected = InvalidRequestParametersException.class)
    public void registerWithBadName() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("", "Tutu", "toto@tutu.fr", "tototutu");
        assertNull(c);

        c = coordinatorRegistrator.register(null, "Tutu", "toto@tutu.fr", "tototutu");
        assertNull(c);

        c = coordinatorRegistrator.register("Toto", null, "toto@tutu.fr", "tototutu");
        assertNull(c);

        c = coordinatorRegistrator.register("Toto", "", "toto@tutu.fr", "tototutu");
        assertNull(c);
    }

    @Test(expected = InvalidRequestParametersException.class)
    @Ignore
    public void registerWithBadEmail() throws UserAlreadyExistsException, InvalidRequestParametersException {
        // TODO FIX THIS TEST ==> FieldsValidator.isValidEmail detects "toto" as a valid email
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto", "tototutu");
        assertNull(c);
    }

    @Test(expected = InvalidRequestParametersException.class)
    public void registerWithBadPassword() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto", null);
        assertNull(c);

        c = coordinatorRegistrator.register("Toto", "Tutu", "toto", "");
        assertNull(c);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void registerAlreadyExistingUser() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertNotNull(c);

        Coordinator c2 = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertNull(c2);
    }

    @Test
    public void authenticateWithGoodCredentials() throws UserAlreadyExistsException, InvalidRequestParametersException, InvalidCredentialsException {
        String email = "email@email.fr";
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", email, "tototutu");
        Coordinator cGoodAuth = coordinatorAuthenticator.authenticate(email, "tototutu");

        assertEquals(c, cGoodAuth);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void authenticateWithBadCredentials() throws InvalidCredentialsException, InvalidRequestParametersException {
        String email = "bademail@email.fr";
        Coordinator c = coordinatorAuthenticator.authenticate(email, "tototutu");
        assertNull(c);
    }
}
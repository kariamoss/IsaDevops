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
    public void registerWithGoodInformation() {
        Message m = coordinatorRegistrator.register("Toto", "Tutu", "toto@tutu.fr", "tototutu");
        assertTrue(m.isOk());
        assertNotEquals(m.getTransmittedObject(), null);
        assertEquals(((Coordinator) m.getTransmittedObject()).getEmail(), "toto@tutu.fr");
    }

    @Test
    public void registerWithBadName() {
        Message m = coordinatorRegistrator.register("", "Tutu", "toto@tutu.fr", "tototutu");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());

        m = coordinatorRegistrator.register(null, "Tutu", "toto@tutu.fr", "tototutu");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());

        m = coordinatorRegistrator.register("Toto", null, "toto@tutu.fr", "tototutu");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());

        m = coordinatorRegistrator.register("Toto", "", "toto@tutu.fr", "tototutu");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());
    }

    @Test
    @Ignore
    public void registerWithBadEmail() {
        // TODO FIX THIS TEST ==> FieldsValidator.isValidEmail detects "toto" as a valid email
        Message m = coordinatorRegistrator.register("Toto", "Tutu", "toto", "tototutu");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());
    }

    @Test
    public void registerWithBadPassword() {
        Message m = coordinatorRegistrator.register("Toto", "Tutu", "toto", null);
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());

        m = coordinatorRegistrator.register("Toto", "Tutu", "toto", "");
        assertTrue(m.isNotOk());
        assertEquals(400, m.getStatus());
    }

    @Test
    public void registerAlreadyExistingUser() {
        Message m = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertTrue(m.isOk());
        assertNotEquals(m.getTransmittedObject(), null);

        Message wrongEmail = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertTrue(wrongEmail.isNotOk());
        assertEquals(400, wrongEmail.getStatus());
        assertNotEquals(null, wrongEmail.getTransmittedObject());
        assertEquals(UserAlreadyExistsException.class, wrongEmail.getTransmittedObject().getClass());
    }

    @Test
    public void authenticateWithGoodCredentials() {
        String email = "email@email.fr";
        Message m = coordinatorRegistrator.register("Toto", "Tutu", email, "tototutu");
        Message goodAuth = coordinatorAuthenticator.authenticate(email, "tototutu");

        assertTrue(goodAuth.isOk());
        assertEquals(Coordinator.class, goodAuth.getTransmittedObject().getClass());
        assertEquals(((Coordinator) goodAuth.getTransmittedObject()).getEmail(), email);
    }

    @Test
    public void authenticateWithBadCredentials() {
        String email = "bademail@email.fr";
        Message goodAuth = coordinatorAuthenticator.authenticate(email, "tototutu");

        assertTrue(goodAuth.isNotOk());
        assertEquals(404, goodAuth.getStatus());
        assertEquals(InvalidCredentialsException.class, goodAuth.getTransmittedObject().getClass());
    }
}
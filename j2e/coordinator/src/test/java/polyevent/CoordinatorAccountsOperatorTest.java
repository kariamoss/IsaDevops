package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Coordinator;
import polyevent.entities.Event;
import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
                .addPackage(Event.class.getPackage())
                .addPackage(Coordinator.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");
    }

    @EJB private ICoordinatorAuthenticator coordinatorAuthenticator;
    @EJB private ICoordinatorRegistrator coordinatorRegistrator;

    @PersistenceContext private EntityManager entityManager;

    @Test
    public void registerWithGoodInformation() throws UserAlreadyExistsException, InvalidRequestParametersException, InvalidCredentialsException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto@tutu.fr", "tototutu");
        assertNotNull(c);
        assertEquals("toto@tutu.fr", c.getEmail());
        assertEquals(c, coordinatorAuthenticator.authenticate(c.getEmail(), c.getPassword()));
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

        assertNull(entityManager.find(Coordinator.class, "toto@tutu.fr"));
    }

    @Test(expected = InvalidRequestParametersException.class)
    public void registerWithBadEmail() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto", "tototutu");
        assertNull(c);
    }

    @Test(expected = InvalidRequestParametersException.class)
    public void registerWithBadPassword() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "toto", null);
        assertNull(c);

        c = coordinatorRegistrator.register("Toto", "Tutu", "toto", "");
        assertNull(c);

        assertNull(entityManager.find(Coordinator.class, "toto"));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void registerAlreadyExistingUser() throws UserAlreadyExistsException, InvalidRequestParametersException {
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertNotNull(c);

        assertNotNull(entityManager.find(Coordinator.class, c.getId()));
        assertEquals(c, entityManager.find(Coordinator.class, c.getId()));

        Coordinator c2 = coordinatorRegistrator.register("Toto", "Tutu", "tutu@tutu.fr", "tototutu");
        assertNull(c2);

        // checks that the register trial of c2 didn't replace c in the database
        assertEquals(c, entityManager.find(Coordinator.class, "tutu@tutu.fr"));
    }

    @Test
    public void authenticateWithGoodCredentials() throws UserAlreadyExistsException, InvalidRequestParametersException, InvalidCredentialsException {
        String email = "email@email.fr";
        Coordinator c = coordinatorRegistrator.register("Toto", "Tutu", email, "tototutu");
        Coordinator cGoodAuth = coordinatorAuthenticator.authenticate(email, "tototutu");

        assertEquals(c, cGoodAuth);
        assertEquals(c.getId(), cGoodAuth.getId());
        assertNotNull(entityManager.find(Coordinator.class, c.getId()));
        assertEquals(c, entityManager.find(Coordinator.class, c.getId()));
        assertEquals(cGoodAuth, entityManager.find(Coordinator.class, c.getId()));
    }

    @Test(expected = InvalidCredentialsException.class)
    public void authenticateWithBadCredentials() throws InvalidCredentialsException, InvalidRequestParametersException {
        String email = "bademail@email.fr";
        Coordinator c = coordinatorAuthenticator.authenticate(email, "tototutu");
        assertNull(c);
        assertNull(entityManager.find(Coordinator.class, "bademail@email.fr"));
    }
}
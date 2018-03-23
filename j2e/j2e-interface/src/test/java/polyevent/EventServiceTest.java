package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import webservice.EventService;

import javax.ejb.EJB;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

@RunWith(Arquillian.class)
public class EventServiceTest{

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(EventService.class.getPackage());
    }

    @EJB private EventService eventService;
    @EJB private Database memory;

    private String coordinatorFalseMail;
    private String coordinatorMail;


    @Before
    public void setUpContext(){
        coordinatorFalseMail = "MarcJourdes@free.fr";
        coordinatorMail = "MarcDu06@laposte.fr";
    }

    @Test
    public void falseMailTest(){
        assertNull(memory.getCoordinatorByMail(coordinatorFalseMail));
    }

    @Test
    public void correctMailTest(){
        assertNull(memory.getCoordinatorByMail(coordinatorMail));
    }
}

package polyevent.event;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.IEventCatalog;
import webservice.event.EventCatalogService;
import webservice.event.IEventCatalogService;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class EventCatalogServiceTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventCatalogService.class.getPackage());
    }

    @EJB
    private IEventCatalogService eventCatalogService;

    @EJB
    private IEventCatalog eventCatalog;

    @Before
    public void setUp() {
        eventCatalogService = new EventCatalogService();
    }

    @Test
    public void testEmptyGetEvents() {
        // todo
    }

    @Test
    public void testGetEvents() {
        // todo
    }

    @Test
    public void testEmptyNameEvent() {
        // todo
    }

    @Test
    public void testNullNameEvent() {
        // todo
    }

    @Test
    public void testGetNameEvent() {
        // todo
    }
}

package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import polyevent.entities.Event;
import polyevent.entities.RoomType;

import javax.ejb.EJB;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MessageTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventOrganizer.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml");

    }

    @EJB private IEventOrganizer organizer;
    @Test
    public void simpleMessage() throws Exception{
        Event event = new Event(100,"salut");

        assertEquals(event,organizer.bookRoom(event));
    }
}
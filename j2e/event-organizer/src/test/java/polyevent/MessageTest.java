package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Arrays;

@RunWith(Arquillian.class)
public class MessageTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(IEventOrganizer.class.getPackage())
                .addPackage(Database.class.getPackage());
    }

    @EJB private IEventOrganizer organizer;
    @Test
    public void simpleMessage() throws Exception{
        Event event = new Event(100,"salut", Arrays.asList(RoomType.LEARNING_CENTRE));

        organizer.bookRoom(event);
    }
}
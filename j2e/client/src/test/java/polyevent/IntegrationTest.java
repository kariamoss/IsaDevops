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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class IntegrationTest {

    @EJB
    private Database db;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(Database.class.getPackage());
    }

    @Test
    @Ignore
    public void createEventTest() {
        Client client = new Client();

        String eventName = UUID.randomUUID().toString();

        String command ="createEvent " + eventName + " 30 MarcDu06@laposte.fr\n";

        InputStream is = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        client.run(is, false, 0);

        assertTrue(db.findEventByName(eventName) != null);
    }
}

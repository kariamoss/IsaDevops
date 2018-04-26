package polyevent;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
@Category(IntegrationTests.class)
@Ignore
public class IntegrationTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void createEventTest() {
        Client client = new Client();

        String eventName = UUID.randomUUID().toString();

        String command ="createEvent " + eventName + " 30 MarcDu06@laposte.fr\n";

        InputStream is = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        client.run(is, false, 0);

        // todo refactor this since the mocked database doesn't exist anymore
        // todo you should consume the EventCatalogService instead of querying the database
        //assertNotNull(db.findEventByName(eventName));
    }
}

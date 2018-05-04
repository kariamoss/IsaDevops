package polyevent;

import com.mashape.unirest.http.exceptions.UnirestException;
import polyevent.entities.Event;

import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class BillCreator implements IBillCreator {

    private Logger l = Logger.getLogger(BillCreator.class.getName());

    @Override
    public boolean createBill(Event event) {
        Mailing mailing = new Mailing(event);
        l.log(Level.INFO, "Sending bill for event " + event.getName() + ".");

        try {
            mailing.sendSimpleMessage();
        } catch (UnirestException e1) {
            e1.printStackTrace();
        }

        return true;
    }
}

package managedbeans;

import polyevent.Event;
import polyevent.EventCatalog;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Optional;

/**
 * Retrieve information from our database, regarding events in particular
 */
@ManagedBean
public class RetrieveEventBean {

    @EJB private EventCatalog eventCatalog;

    public Optional<List<Event>> getEvents(){
        return eventCatalog.getAllEvents();
    }
}

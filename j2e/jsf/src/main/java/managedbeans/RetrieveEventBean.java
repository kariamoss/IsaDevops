package managedbeans;

import polyevent.IEventCatalog;
import polyevent.entities.Event;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Optional;

/**
 * Retrieve information from our database, regarding events in particular
 */
@ManagedBean
public class RetrieveEventBean {

    @EJB private IEventCatalog eventCatalog;

    public List<Event> getEvents(){
        Optional<List<Event>> events = eventCatalog.getAllEvents();
        return events.orElse(null);
    }
}

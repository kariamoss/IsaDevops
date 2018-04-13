package managedbeans;

import javax.faces.bean.ManagedBean;

/**
 * Retrieve information from our database, regarding events in particular
 */
@ManagedBean
public class EventBean {
    public String getEventName(){
        return "Hello world";
    }
}

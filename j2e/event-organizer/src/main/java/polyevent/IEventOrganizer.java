package polyevent;


import polyevent.communication.Message;

import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    Message bookRoom(Event event);

}

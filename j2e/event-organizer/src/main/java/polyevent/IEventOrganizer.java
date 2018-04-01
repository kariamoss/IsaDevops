package polyevent;


import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    Message bookRoom(Event event);

}

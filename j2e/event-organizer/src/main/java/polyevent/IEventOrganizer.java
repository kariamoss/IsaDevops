package polyevent;


import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    boolean bookRoom(Event event);

}

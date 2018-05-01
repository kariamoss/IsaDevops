package polyevent;


import polyevent.entities.Event;
import polyevent.entities.Room;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IRoomBooker {

    void addBooking(BookingTask task);
}

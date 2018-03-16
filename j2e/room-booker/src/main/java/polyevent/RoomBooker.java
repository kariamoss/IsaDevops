package polyevent;

import java.util.List;

/**
 * Hello world!
 *
 */
public class RoomBooker implements IRoomBooker
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public boolean book(List<Room> rooms, Event event) {
        return false;
    }

    @Override
    public boolean cancelRoomBooking(List<Room> rooms, Event event) {
        return false;
    }
}

package polyevent;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class EventCreator implements IEventCreator
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public boolean registerEvent(String name, int participantNumber, Date date, Coordinator coordinator) {
        return false;
    }

    @Override
    public boolean cancelEvent(Event event) {
        return false;
    }
}

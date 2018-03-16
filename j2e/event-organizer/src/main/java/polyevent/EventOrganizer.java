package polyevent;

/**
 * Hello world!
 *
 */
public class EventOrganizer implements IEventOrganizer
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public boolean bookRoom(Event event) {
        return false;
    }
}

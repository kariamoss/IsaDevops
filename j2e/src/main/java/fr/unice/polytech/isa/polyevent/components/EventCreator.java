package fr.unice.polytech.isa.polyevent.components;

import fr.unice.polytech.isa.polyevent.IEventCreator;
import fr.unice.polytech.isa.polyevent.entities.Coordinator;
import fr.unice.polytech.isa.polyevent.entities.Event;

import javax.ejb.Stateless;
import java.util.Date;


@Stateless
public class EventCreator implements IEventCreator {
    @Override
    public boolean registerEvent(String name, int participantNumber, Date date, Coordinator coordinator) {
        return false;
    }

    @Override
    public boolean cancelEvent(Event event) {
        return false;
    }
}

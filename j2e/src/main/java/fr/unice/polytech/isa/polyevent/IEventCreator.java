package fr.unice.polytech.isa.polyevent;

import fr.unice.polytech.isa.polyevent.entities.Coordinator;
import fr.unice.polytech.isa.polyevent.entities.Event;

import java.util.Date;

import javax.ejb.Local;

@Local
public interface IEventCreator {

    boolean registerEvent(String name, int participantNumber, Date date, Coordinator coordinator);

    boolean cancelEvent(Event event);
}

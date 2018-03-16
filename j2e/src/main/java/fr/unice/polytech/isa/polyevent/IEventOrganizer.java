package fr.unice.polytech.isa.polyevent;


import fr.unice.polytech.isa.polyevent.entities.Event;

import javax.ejb.Local;

@Local
public interface IEventOrganizer {

    boolean bookRoom(Event event);

}

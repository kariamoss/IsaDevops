package fr.unice.polytech.isa.polyevent.components;

import fr.unice.polytech.isa.polyevent.IEventOrganizer;
import fr.unice.polytech.isa.polyevent.entities.Event;

import javax.ejb.Stateless;


@Stateless
public class EventOrganizer implements IEventOrganizer {
    @Override
    public boolean bookRoom(Event event) {
        return false;
    }
}

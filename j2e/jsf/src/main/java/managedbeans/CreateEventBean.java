package managedbeans;

import polyevent.IEventCreator;
import polyevent.entities.Coordinator;
import polyevent.exceptions.*;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@ManagedBean
@SessionScoped
public class CreateEventBean implements Serializable
{
    @EJB private IEventCreator eventCreator;

    @ManagedProperty("#{authenticateBean.coordinator}")
    private Coordinator coordinator;

    private String eventName;
    private int nbPeople;
    private Date date;

    public String createEvent(){
        date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        try {
            eventCreator.registerEvent(eventName, nbPeople, calendar, coordinator);
            return Signal.ADDED;
        } catch (InvalidRequestParametersException | DatabaseSavingException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("Un problème est survenu lors de la création de l'évenement"));
            return Signal.ADDED_ERROR;
        } catch (RoomNotAvailableException | InvalidRoomException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("Malheureusement, aucune salle n'est disponible actuellement"));
            return Signal.ADDED_ERROR;
        } catch (ExternalServiceCommunicationException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("Un problème est survenu lors de la communication avec le serveur"));
            return Signal.ADDED_ERROR;
        }
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getNbPeople() {
        return nbPeople;
    }
    public void setNbPeople(int nbPeople) {
        this.nbPeople = nbPeople;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}

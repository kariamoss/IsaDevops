package managedbeans;

import polyevent.ICoordinatorRegistrator;
import polyevent.exceptions.InvalidRequestParametersException;
import polyevent.exceptions.UserAlreadyExistsException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class CreateCoordinatorBean {

    @EJB private ICoordinatorRegistrator coordinatorRegistrator;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public String createCoordinator(){
        try {
            coordinatorRegistrator.register(firstName, lastName, email, password);
            return Signal.ADDED;
        } catch (InvalidRequestParametersException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("Un problème est survenu lors de la création du coordinateur"));
            return Signal.ADDED_ERROR;
        } catch (UserAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("Un utilisateur qui possède cette adresse mail est déjà inscrit"));
            return Signal.ADDED_ERROR;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

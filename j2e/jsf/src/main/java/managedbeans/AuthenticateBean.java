package managedbeans;

import polyevent.ICoordinatorAuthenticator;
import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidCredentialsException;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class AuthenticateBean implements Serializable {
    @EJB private ICoordinatorAuthenticator coordinatorAuthenticator;

    private String email;
    private String password;
    private Coordinator coordinator;

    public String authenticate(){
        try {
            this.coordinator = coordinatorAuthenticator.authenticate(email, password);
            return Signal.LOGGED;
        } catch (InvalidCredentialsException | InvalidRequestParametersException e) {
            FacesContext.getCurrentInstance().addMessage("form-error",
                    new FacesMessage("L'email et le mot de passe ne correspondent pas"));
            return Signal.LOGIN_ERROR;
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

    public Coordinator getCoordinator() {
        return coordinator;
    }
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}

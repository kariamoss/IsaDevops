package webservice.event;

import polyevent.ICoordinatorRegistrator;
import polyevent.Message;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless
public class CoordinatorService implements ICoordinatorService {

    @EJB public ICoordinatorRegistrator coordinatorRegistrator;

    @Override
    public Message register(String firstName, String lastName, String email, String password) {
        return coordinatorRegistrator.register(firstName, lastName, email, password);
    }
}

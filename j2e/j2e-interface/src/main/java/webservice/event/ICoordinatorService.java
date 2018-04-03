package webservice.event;

import polyevent.Coordinator;
import polyevent.Message;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ICoordinatorService {
    /**
     * Consume the ICoordinatorRegistrator bean to register a new {@link Coordinator}
     * The newly created {@link Coordinator} is passed through the
     * {@link Message} if the registration was successful, otherwise
     * an {@link Exception} describing the problem
     * should be passed as a return value
     *
     * @param firstName the first name for this account
     * @param lastName the last name for this account
     * @param email the email of this account, used for further authentication
     * @param password the password of this account, used for further authentication
     * @return a {@link Message} object containing the result of the registration
     *         process
     */
    @WebMethod
    Message register(String firstName, String lastName, String email, String password);
}

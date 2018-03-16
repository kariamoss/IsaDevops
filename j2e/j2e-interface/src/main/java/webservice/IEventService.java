package webservice;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IEventService {

    @WebMethod
    void createEvent(@WebParam(name="name") String name);
}

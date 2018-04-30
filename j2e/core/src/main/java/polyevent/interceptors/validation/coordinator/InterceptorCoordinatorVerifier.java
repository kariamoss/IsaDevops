package polyevent.interceptors.validation.coordinator;

import polyevent.FieldsValidator;
import polyevent.entities.Coordinator;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A simple interceptor to verify that a given coordinator
 * is valid, i.e., it is not null, and it exists in the database
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class InterceptorCoordinatorVerifier {

    @PersistenceContext private EntityManager entityManager;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Coordinator coordinator = null;
        Object[] parameters = ctx.getParameters();
        
        // gets the coordinator parameter
        for (Object parameter : parameters) {
            if (parameter.getClass().getName().equals(Coordinator.class.getName())) {
                coordinator = (Coordinator) parameter;
            }
        }

        // now we can do the validation
        if (FieldsValidator.isObjectNotNull(coordinator)) {
            // checks that the given user actually exists in the database
            if (entityManager.find(Coordinator.class, coordinator.getId()) == null) {
                throw new InvalidRequestParametersException("The given coordinator doesn't exist in the database!");
            }
            else {
                return ctx.proceed();
            }

        }
        else {
            throw new InvalidRequestParametersException("The coordinator parameter is null !");
        }
    }
}

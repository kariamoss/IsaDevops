package polyevent.interceptors.validation.standard;

import polyevent.FieldsValidator;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class InterceptorStringVerifier {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();

        // gets the coordinator parameter
        for (Object parameter : parameters) {
            if (parameter == null) {
                throw new InvalidRequestParametersException("One or more parameters of the request are null!");
            }

            // if the parameter is of type String
            if (parameter.getClass().getName().equals(String.class.getName())) {
                // if the string is invalid
                if (!FieldsValidator.isStringValid((String) parameter)) {
                    throw new InvalidRequestParametersException("One or more parameters of type String in the request are invalid!");
                }
            }
        }
        return ctx.proceed();
    }
}

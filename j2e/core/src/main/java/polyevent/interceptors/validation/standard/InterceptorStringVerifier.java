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
            // if the parameter is of type String
            if (parameter.getClass().getName().equals(String.class.getName())) {
                // if the string is invalid
                if (!FieldsValidator.isStrictlyPositive((int) parameter)) {
                    throw new InvalidRequestParametersException("The parameter of type int should be strictly positive!");
                }
            }
        }
        return ctx.proceed();
    }
}

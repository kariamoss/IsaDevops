package polyevent.interceptors.validation.standard;

import polyevent.FieldsValidator;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class InterceptorIntegerVerifier {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();

        // gets the coordinator parameter
        for (Object parameter : parameters) {
            if (parameter == null) {
                throw new InvalidRequestParametersException("One or more parameters of of the request are null!");
            }

            // if the parameter is of type Integer
            if (parameter.getClass().getName().equals(Integer.class.getName())) {
                // if the integer is negative or zero
                if (!FieldsValidator.isStrictlyPositive((int) parameter)) {
                    throw new InvalidRequestParametersException("The parameter of type int should be strictly positive!");
                }
            }
        }
        return ctx.proceed();
    }
}

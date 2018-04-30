package polyevent.interceptors.validation.email;

import polyevent.FieldsValidator;
import polyevent.exceptions.InvalidRequestParametersException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class InterceptorEmailVerifier {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        int emailIndex = ctx.getMethod().getName().equals("register") ? 2 : 0;
        String email = (String) ctx.getParameters()[emailIndex];

        if (email == null) {
            throw new InvalidRequestParametersException("The email of the request cannot be null!");
        }
        if (!FieldsValidator.isValidEmail(email)) {
            throw new InvalidRequestParametersException("The email is not valid!");
        }
        return ctx.proceed();
    }
}

package polyevent.validation;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

/**
 * Custom interceptor implementation
 * to validate event constructor parameters.
 */
@Interceptor
@ConstructorValidationInterceptorBinding
public class EventValidationInterceptor {

    @Inject
    private Validator validator;

    @AroundInvoke
    public Object validateMethodInvocation(InvocationContext ctx)
            throws Exception {
        Set<ConstraintViolation<Object>> violations;
        ExecutableValidator executableValidator = validator.forExecutables();
        violations = executableValidator.validateParameters(
                ctx.getTarget(), ctx.getMethod(), ctx.getParameters());
        processViolations(violations);
        Object result = ctx.proceed();
        violations = executableValidator.validateReturnValue(
                ctx.getTarget(), ctx.getMethod(), result);
        processViolations(violations);
        return result;
    }

    private void processViolations(Set<ConstraintViolation<Object>> violations) {
        /* TODO : do this in dev mode
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
       */
        if (violations.size() > 0)
            throw new ConstraintViolationException("A validation error occurred with the event creation", violations);
    }

}

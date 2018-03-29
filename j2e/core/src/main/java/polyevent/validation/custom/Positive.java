package polyevent.validation.custom;

import javax.validation.Constraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom validator annotation to
 * perform integer analysis at runtime
 * This class ensures that an integer annotated with {@link Positive}
 * will either be positive, or throw a {@link javax.validation.ConstraintViolationException}
 * if it's zero or negative
 */
@Pattern(regexp = "[1-9]+") // Integer of value >= 1
@Size(min = 1, message = "Integer length must at least be 1")
@Constraint(validatedBy = PositiveIntegerValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Positive {
    String message() default "The number of people at the event must be at least 1";
}

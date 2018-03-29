package polyevent.validation.custom;

import org.apache.bval.constraints.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveIntegerValidator implements ConstraintValidator<NotEmpty, Integer> {
    public PositiveIntegerValidator() {
    }

    public void initialize(NotEmpty constraintAnnotation) {
    }

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value > 0;
    }
}

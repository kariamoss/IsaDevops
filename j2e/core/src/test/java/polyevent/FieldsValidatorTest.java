package polyevent;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class FieldsValidatorTest {
    @Test
    public void testStringValidity() {
        assertFalse(FieldsValidator.isStringValid(null));
        assertFalse(FieldsValidator.isStringValid(""));
        assertTrue(FieldsValidator.isStringValid("A string"));
    }

    @Test
    public void testIntegerValidity() {
        assertFalse(FieldsValidator.isStrictlyPositive(-3));
        assertFalse(FieldsValidator.isStrictlyPositive(0));
        assertTrue(FieldsValidator.isStrictlyPositive(1));
    }

    @Test
    public void testObjectValidity() {
        assertFalse(FieldsValidator.isObjectNotNull(null));
        assertTrue(FieldsValidator.isObjectNotNull(new Object()));
    }

    @Test
    public void testEmailValidity() {
        // some tests to have an idea of how the email should be formatted
        // since this check is done using the apache-commons library
        // this check is already tested
        assertFalse(FieldsValidator.isValidEmail(null));
        assertFalse(FieldsValidator.isValidEmail(""));
        assertFalse(FieldsValidator.isValidEmail("toto"));
        assertFalse(FieldsValidator.isValidEmail("toto@"));
        assertFalse(FieldsValidator.isValidEmail("toto.com"));
        assertTrue(FieldsValidator.isValidEmail("toto@gmail.com"));
    }

    @Test
    public void testDateValidity() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 2);
        assertFalse(FieldsValidator.dateIsGood(Calendar.getInstance()));
        assertTrue(FieldsValidator.dateIsGood(cal));
    }
}
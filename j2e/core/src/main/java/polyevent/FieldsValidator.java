package polyevent;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Calendar;

/**
 * A static helper class used to validate fields within the EJBs
 *
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class FieldsValidator {

    /**
     * Returns true if the given string is non null and
     * is not empty
     *
     * @param str the string to validate
     * @return true if the string is semantically correct
     */
    public static boolean isStringValid(String str) {
        return str != null && !str.equals("");
    }

    /**
     * Returns true if the given value is strictly positive
     *
     * @param value the number to check
     * @return true if the value is strictly positive
     */
    public static boolean isStrictlyPositive(int value) {
        return value > 0;
    }

    /**
     * Checks if the given date is a date in the future,
     * and returns true if it is
     *
     * @param date the date to check
     * @return true if the provided start date is valid
     */
    public static boolean dateIsGood(Calendar date) {
        return date.after(Calendar.getInstance());
    }

    /**
     * Checks if the given {@link Object} is a valid object,
     * i.e., if he's not null, and returns true if he is not
     * @param object the {@link Object} to check
     * @return true if the {@link Object} is valid
     */
    public static boolean isObjectNotNull(Object object) {
        return object != null;
    }

    /**
     * Checks that the provided email is correct, and returns true if it is,
     * or false otherwise
     *
     * @implNote this method doesn't check that the provided email
     *           actually exists, but it only checks if the email
     *           is valid according to the RFC
     *
     * <a href="https://stackoverflow.com/a/5931718/5710894">Inspired by this</a>
     *
     * @param email the email to check
     * @return true if the given email is valid
     */
    public static boolean isValidEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            return false;
        }
        return true;
    }
}

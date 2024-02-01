package org.example.validation;
import org.example.utils.Constants;

public class Validation {
    public static boolean stringAllCharValidator(String passedString)
    {
        boolean containsNumber = passedString.matches(".*\\d+.*");
        return containsNumber;
    }
    public static boolean allNumericValidatorInString(String passedString)
    {
        Boolean allNumericApproved = passedString.matches("\\d+");
        return allNumericApproved;
    }
    public static boolean floatingNumberValidatorInString(String passedString)
    {
        String stringPattern = "^\\d+(\\.\\d+)?$";
        Boolean allFloatApproved=passedString.matches(stringPattern);
        return allFloatApproved;
    }
    public static boolean mobileNumberValidatorInString(String passedString)
    {
        if(allNumericValidatorInString(passedString)&&(passedString.length()==Constants.MOBILE11DIGITS)) {
            return true;
        }
        return false;
    }
    public static boolean emailValidator(String passedString)
    {
        String email = "user@example.com";
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean isValid = email.matches(regex);
        return isValid;
    }


}

package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateDateFormat(String s) {
        Pattern p = Pattern.compile("20\\d{2}(-|\\/)((0[1-9])|(1[0-2]))(-|\\/)((0[1-9])|([1-2][0-9])|(3[0-1]))");
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
    public static boolean validateIntegers(String i) {
        Pattern p = Pattern.compile("^([0-9]*)/$");
        Matcher m = p.matcher(i);
        return m.matches();
    }
}

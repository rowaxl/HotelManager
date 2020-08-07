package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateDateFormat(String s) {
        if (s == null) {
            return false;
        }

        Pattern p = Pattern.compile("20\\d{2}(-|\\/)((0[1-9])|(1[0-2]))(-|\\/)((0[1-9])|([1-2][0-9])|(3[0-1]))");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean validateEmailAdd(String s) {
        if (s == null) {
            return false;
        }

    	Pattern p = Pattern.compile("^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$");
    	Matcher m = p.matcher(s);
    	return m.matches();
    }
    
    public static boolean validatePhoneNum(String s) {
        if (s == null) {
            return false;
        }

    	Pattern p = Pattern.compile("^\\d{2,4}-\\d{2,4}-\\d{4}$");
    	Matcher m = p.matcher(s);
    	return m.matches();
    }
    
}

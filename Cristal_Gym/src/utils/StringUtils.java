package utils;

public class StringUtils {

    public static boolean ehNumero(CharSequence cs) {
        if (cs != null && cs.length() != 0) {
            for(int i = 0; i < cs.length(); ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}

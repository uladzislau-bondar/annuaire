package util;


import java.sql.Date;

public class StringUtils {

    public static Date emptyToDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Date.valueOf(value);
        }
    }

    public static int emptyToInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(value);
        }
    }

    public static String intToString(int value) {
        if (value == 0) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    static String nullToEmpty(String value){
        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value;
        }
    }
}

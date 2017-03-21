package util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }

        return queryPairs;
    }

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

    static String nullToEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value;
        }
    }

    public static List<Long> valuesOf(String[] values){
        List<Long> longValues = new ArrayList<>();
        for (String value: values){
            longValues.add(Long.valueOf(value));
        }

        return longValues;
    }
}

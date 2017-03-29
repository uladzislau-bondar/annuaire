package util;


import enums.PhoneType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.*;

public class MyStringUtils {
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

    public static List<Long> stringArrayToListOfLongs(String[] values){
        List<Long> longValues = new ArrayList<>();
        if (values != null){
            for (String value: values){
                longValues.add(Long.valueOf(value));
            }
        }

        return longValues;
    }

    public static List<Integer> stringArrayToListOfIntegers(String[] values){
        List<Integer> integerValues = new ArrayList<>();
        if (values != null){
            for (String value: values){
                integerValues.add(Integer.valueOf(value));
            }
        }

        return integerValues;
    }

    public static List<String> stringArrayToListOfStrings(String[] values){
        List<String> stringValues = new ArrayList<>();
        if (values != null){
            stringValues = Arrays.asList(values);
        }

        return stringValues;
    }
}

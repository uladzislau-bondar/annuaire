package util;


import enums.PhoneType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
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

    public static String joinListWithSemicolon(List<String> strings) {
        return String.join("; ", strings);
    }

    public static List<Long> toLongList(String[] array) {
        List<Long> list = new ArrayList<>();
        if (array != null){
            list = Arrays.stream(array).map(Long::valueOf).collect(Collectors.toList());
        }

        return list;
    }


}

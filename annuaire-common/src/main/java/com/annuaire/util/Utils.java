package com.annuaire.util;


import com.annuaire.dto.ContactInitialsDto;
import com.annuaire.exceptions.ServiceException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static Map<String, String> splitQuery(String query) throws ServletException, UnsupportedEncodingException {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                if (idx >= 0){
                    queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                } else{
                    throw new ServletException("Invalid params");
                }
            }
        }

        return queryPairs;
    }

    public static Date emptyToDate(String date) {
        if (StringUtils.isNotEmpty(date)) {
            return Date.valueOf(date);
        } else {
            return null;
        }
    }

    public static String emptyToMaritalStatus(String status){
        if (StringUtils.isNotEmpty(status)) {
            return status;
        } else {
            return null;
        }
    }

    public static String joinListWithSemicolon(List<String> strings) {
        return String.join("; ", strings);
    }

    public static String buildBirthdayBoysListMessage(List<ContactInitialsDto> contacts){
        StringBuilder result = new StringBuilder();
        for (ContactInitialsDto contact : contacts) {
            result.append(contact.getFirstName());
            result.append(" ");
            result.append(contact.getLastName());
            if (contact.getEmail() != null){
                result.append("(");
                result.append(contact.getEmail());
                result.append(")");
            }
            result.append("; ");
        }

        return result.toString();
    }

    public static List<Long> toLongList(String[] array) {
        List<Long> list = new ArrayList<>();
        if (array != null){
            list = Arrays.stream(array).map(Long::valueOf).collect(Collectors.toList());
        }

        return list;
    }

    public static List<String> deleteNulls(List<String> list){
        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}

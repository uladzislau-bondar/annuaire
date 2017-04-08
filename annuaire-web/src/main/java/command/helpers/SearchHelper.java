package command.helpers;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchHelper extends AbstractHelper{
    public SearchHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public Map<String, String> getSearchParams(){
        Map<String, String> params = new HashMap<>();

        if (StringUtils.isNotEmpty(request.getParameter("firstName"))){
            params.put("firstName", request.getParameter("firstName"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("lastName"))){
            params.put("lastName", request.getParameter("lastName"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("middleName"))){
            params.put("middleName", request.getParameter("middleName"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("dateRadio"))){
            params.put("dateRange", request.getParameter("dateRadio"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("dateOfBirth"))){
            params.put("dateOfBirth", request.getParameter("dateOfBirth"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("sex"))){
            params.put("sex", request.getParameter("sex"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("citizenship"))){
            params.put("citizenship", request.getParameter("citizenship"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("maritalStatus"))){
            params.put("maritalStatus", request.getParameter("maritalStatus"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("country"))){
            params.put("country", request.getParameter("country"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("city"))){
            params.put("city", request.getParameter("city"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("address"))){
            params.put("address", request.getParameter("address"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("zip"))){
            params.put("zip", request.getParameter("zip"));
        }

        return params;
    }
}

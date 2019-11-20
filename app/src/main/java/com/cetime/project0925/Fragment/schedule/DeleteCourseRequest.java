package com.cetime.project0925.Fragment.schedule;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteCourseRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteCourseRequest(String userID, String courseIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseIndex", courseIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}

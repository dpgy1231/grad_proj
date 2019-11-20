package com.cetime.project0925.Fragment.schedule;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddCourseRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CourseAdd.php";
    private Map<String, String> parameters;

    public AddCourseRequest(String userID, String courseID, String courseIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
        parameters.put("courseIndex", courseIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}

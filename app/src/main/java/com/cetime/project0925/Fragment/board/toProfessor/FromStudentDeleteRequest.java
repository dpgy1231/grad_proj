package com.cetime.project0925.Fragment.board.toProfessor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FromStudentDeleteRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/FromStudentDelete.php";
    private Map<String, String> parameters;

    public FromStudentDeleteRequest(String forProIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("forProIndex", forProIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
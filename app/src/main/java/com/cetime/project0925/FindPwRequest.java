package com.cetime.project0925;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindPwRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/UserFindPw.php";
    private Map<String, String> parameters;

    public FindPwRequest(String userID, String userPassword, String userEmail, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userEmail",userEmail);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}

package com.cetime.project0925.Fragment.mypage;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.cetime.project0925.Fragment.MypageFrag;

import java.util.HashMap;
import java.util.Map;

public class MypageRequest extends StringRequest {

    final static private String URL = "https://coe516.cafe24.com/UserMypage.php";
    private Map<String, String> parameters;

    public MypageRequest(String userID, String userPassword, String userName, String userGender, String userGrade, String userEmail, String userPhone, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userGender", userGender);
        parameters.put("userGrade", userGrade);
        parameters.put("userEmail", userEmail);
        parameters.put("userPhone", userPhone);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}

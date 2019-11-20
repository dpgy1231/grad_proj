package com.cetime.project0925.Fragment.board.proNotice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProNoticeDeleteRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ProNoticeDelete.php";
    private Map<String, String> parameters;

    public ProNoticeDeleteRequest(String proIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("proIndex", proIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
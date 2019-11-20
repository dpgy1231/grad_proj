package com.cetime.project0925.Fragment.chat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatDeleteRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ChatDelete.php";
    private Map<String, String> parameters;

    public ChatDeleteRequest(String chatIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("chatIndex", chatIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
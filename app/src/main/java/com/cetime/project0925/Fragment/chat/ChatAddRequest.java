package com.cetime.project0925.Fragment.chat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatAddRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ChatAdd.php";
    private Map<String, String> parameters;

    public ChatAddRequest(String chatID1, String chatName1, String chatID2, String chatName2, String chatContent, String chatDate, String chatTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("chatID1",chatID1);
        parameters.put("chatName1",chatName1);
        parameters.put("chatID2",chatID2);
        parameters.put("chatName2",chatName2);
        parameters.put("chatContent",chatContent);
        parameters.put("chatDate",chatDate);
        parameters.put("chatTime",chatTime);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
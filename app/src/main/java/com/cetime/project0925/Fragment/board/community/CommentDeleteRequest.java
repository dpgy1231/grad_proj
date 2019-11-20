package com.cetime.project0925.Fragment.board.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentDeleteRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CommentDelete.php";
    private Map<String, String> parameters;

    public CommentDeleteRequest(String commentIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("commentIndex", commentIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
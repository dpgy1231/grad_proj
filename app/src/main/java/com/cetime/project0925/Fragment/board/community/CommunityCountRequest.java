package com.cetime.project0925.Fragment.board.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommunityCountRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CommunityCountUp.php";
    private Map<String, String> parameters;

    public CommunityCountRequest(String communityIndex, String communityCount, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("communityIndex",communityIndex);
        parameters.put("communityCount",communityCount);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
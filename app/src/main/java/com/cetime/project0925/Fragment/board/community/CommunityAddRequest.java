package com.cetime.project0925.Fragment.board.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommunityAddRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CommunityAdd.php";
    private Map<String, String> parameters;

    public CommunityAddRequest(String communityTag, String communityTitle, String communityContent, String communityWriter, String communityDate, String communityTime, String communityUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("communityTag",communityTag);
        parameters.put("communityTitle",communityTitle);
        parameters.put("communityContent",communityContent);
        parameters.put("communityWriter",communityWriter);
        parameters.put("communityDate",communityDate);
        parameters.put("communityTime",communityTime);
        parameters.put("communityUserID",communityUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
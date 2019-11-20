package com.cetime.project0925.Fragment.board.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommunityModifyRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CommunityModify.php";
    private Map<String, String> parameters;

    public CommunityModifyRequest(String communityIndex, String communityTag, String communityTitle, String communityContent, String communityDate, String communityTime, String communityUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("communityIndex", communityIndex);
        parameters.put("communityTag", communityTag);
        parameters.put("communityTitle", communityTitle);
        parameters.put("communityContent", communityContent);
        parameters.put("communityDate", communityDate);
        parameters.put("communityTime", communityTime);
        parameters.put("communityUserID", communityUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
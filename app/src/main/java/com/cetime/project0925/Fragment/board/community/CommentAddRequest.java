package com.cetime.project0925.Fragment.board.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentAddRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/CommentAdd.php";
    private Map<String, String> parameters;

    public CommentAddRequest(String communityIndex, String commentTag, String commentWriter, String commentContent, String commentDate, String commentTime, String commentUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("communityIndex",communityIndex);
        parameters.put("commentTag",commentTag);
        parameters.put("commentWriter",commentWriter);
        parameters.put("commentContent",commentContent);
        parameters.put("commentDate",commentDate);
        parameters.put("commentTime",commentTime);
        parameters.put("commentUserID",commentUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
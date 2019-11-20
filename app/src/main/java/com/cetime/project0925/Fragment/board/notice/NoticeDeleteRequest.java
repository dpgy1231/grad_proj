package com.cetime.project0925.Fragment.board.notice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeDeleteRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/NoticeDelete.php";
    private Map<String, String> parameters;

    public NoticeDeleteRequest(String noticeIndex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("noticeIndex", noticeIndex);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
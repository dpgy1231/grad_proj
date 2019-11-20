package com.cetime.project0925.Fragment.board.notice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeAddRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/NoticeAdd.php";
    private Map<String, String> parameters;

    public NoticeAddRequest(String noticeTag, String noticeTitle, String noticeContent, String noticeDate, String noticeTime, String noticeUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("noticeTag",noticeTag);
        parameters.put("noticeTitle",noticeTitle);
        parameters.put("noticeContent",noticeContent);
        parameters.put("noticeDate",noticeDate);
        parameters.put("noticeTime",noticeTime);
        parameters.put("noticeUserID",noticeUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
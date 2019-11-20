package com.cetime.project0925.Fragment.board.proNotice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProNoticeAddRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ProNoticeAdd.php";
    private Map<String, String> parameters;

    public ProNoticeAddRequest(String proTag, String proTitle, String proContent, String proWriter, String proDate, String proTime, String proUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("proTag",proTag);
        parameters.put("proTitle",proTitle);
        parameters.put("proContent",proContent);
        parameters.put("proWriter",proWriter);
        parameters.put("proDate",proDate);
        parameters.put("proTime",proTime);
        parameters.put("proUserID",proUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
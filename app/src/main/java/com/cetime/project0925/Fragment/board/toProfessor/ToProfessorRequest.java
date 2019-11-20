package com.cetime.project0925.Fragment.board.toProfessor;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ToProfessorRequest extends StringRequest {

    final static private String URL = "http://coe516.cafe24.com/ToProfessor.php";
    private Map<String, String> parameters;

    public ToProfessorRequest(String forProTag, String forProName, String forProTitle, String forProContent, String forProWriter, String forProDate, String forProTime, String forProUserID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("forProTag",forProTag);
        parameters.put("forProName",forProName);
        parameters.put("forProTitle",forProTitle);
        parameters.put("forProContent",forProContent);
        parameters.put("forProWriter",forProWriter);
        parameters.put("forProDate",forProDate);
        parameters.put("forProTime",forProTime);
        parameters.put("forProUserID",forProUserID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
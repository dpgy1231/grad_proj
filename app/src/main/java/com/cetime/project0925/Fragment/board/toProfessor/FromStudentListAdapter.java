package com.cetime.project0925.Fragment.board.toProfessor;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.board.community.Community;
import com.cetime.project0925.Fragment.board.community.Community2;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.cetime.project0925.Fragment.board.community.CommunityActivity.community2List;

public class FromStudentListAdapter extends BaseAdapter {
    private Context context;
    private List<FromStudent> fromStudentList;


    public FromStudentListAdapter(Context context, List<FromStudent> fromStudentList) {
        this.context = context;
        this.fromStudentList = fromStudentList;
    }

    @Override
    public int getCount() {
        return fromStudentList.size();
    }

    @Override
    public Object getItem(int i) {
        return fromStudentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_from_student, null);

            TextView fromStuTitle = (TextView) v.findViewById(R.id.fromStuTitle);
            TextView fromStuWriter = (TextView) v.findViewById(R.id.fromStuWriter);
            TextView fromStuDate = (TextView) v.findViewById(R.id.fromStuDate);
            TextView fromStuTime = (TextView) v.findViewById(R.id.fromStuTime);

            fromStuTitle.setText(fromStudentList.get(i).getTitle());
            fromStuWriter.setText(fromStudentList.get(i).getWriter());
            String fromStuDateString = fromStudentList.get(i).getDate();
            String fromStuTimeString = fromStudentList.get(i).getTime();
            fromStuDate.setText(fromStuDateString.substring(5, 7) + "/" + fromStuDateString.substring(8, 10));
            fromStuTime.setText(fromStuTimeString.substring(0, 2) + ":" + fromStuTimeString.substring(3, 5));

            v.setTag(fromStudentList.get(i).getTitle());

        return v;
    }
    public void remove(int position){
        fromStudentList.remove(position);
    }

}

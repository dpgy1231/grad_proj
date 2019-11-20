package com.cetime.project0925.Fragment.CEinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.schedule.AddCourseRequest;
import com.cetime.project0925.Fragment.schedule.Course;
import com.cetime.project0925.Fragment.schedule.Schedule;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private String userID = HomeActivity.userID;
    private List<String> courseIDList;
    private int hour, minute, second;
    private String time;
    private String start = "no",end = "no";
    private String dayOfWeekString;
    public String selectedDay = " ";

    public ClassroomAdapter(Context context, List<Course> courseList, int hour, int minute, int second, String dayOfWeekString) {
        this.context = context;
        this.courseList = courseList;
        courseIDList = new ArrayList<String>();
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.dayOfWeekString = dayOfWeekString;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_classroom, null);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        String subTime= courseList.get(i).getCourseTime();
        int now=0, now21=0;
        if(courseList.get(i).getCourseTime().contains(",")){
            int dayIndex = courseList.get(i).getCourseTime().indexOf(selectedDay);
            int commaIndex = courseList.get(i).getCourseTime().indexOf(",");
            if(dayIndex <= commaIndex){
                subTime = courseList.get(i).getCourseTime().substring(dayIndex, commaIndex);
            }
            else{
                subTime = courseList.get(i).getCourseTime().substring(commaIndex, subTime.length());
            }
        }
        //21-28교시
        if(hour==9 || ((hour==10) && (minute<15))){now21=21;}
        if(((hour==10) && (minute>=30)) || ((hour==11) && (minute<45))){now21=22;}
        if(hour==12 || ((hour==13) && (minute<15))){now21=23;}
        if(((hour==13) && (minute>=30)) || ((hour==14) && (minute<45))){now21=24;}
        if(hour==15 || ((hour==16) && (minute<15))){now21=25;}
        if(((hour==16) && (minute>=30)) || ((hour==17) && (minute<45))){now21=26;}

        //1-13교시
        if(hour==9 && minute<50){now=1;}
        if(hour==10 && minute<50){now=2;}
        if(hour==11 && minute<50){now=3;}
        if(hour==12 && minute<50){now=4;}
        if(hour==13 && minute<50){now=5;}
        if(hour==14 && minute<50){now=6;}
        if(hour==15 && minute<50){now=7;}
        if(hour==16 && minute<50){now=8;}
        if(hour==17 && minute<50){now=9;}
        if(hour==18 && minute<50){now=10;}
        if(hour==19 && minute<50){now=11;}
        if(hour==20 && minute<50){now=12;}
        if(hour==21 && minute<50){now=13;}

        if(subTime.contains("[21]")){
            start="9:00"; end="10:15";
            if(now21==21 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[22]")){
            if(start.equals("no")) start="10:30";
            end="11:45";
            if(now21==22 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[23]")){
            if(start.equals("no")) start="12:00";
            end="13:15";
            if(now21==23 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[24]")){
            if(start.equals("no")) start="13:30";
            end="14:45";
            if(now21==24 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[25]")){
            if(start.equals("no")) start="15:00";
            end="16:15";
            if(now21==25 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[26]")){
            if(start.equals("no")) start="16:30";
            end="17:45";
            if(now21==26 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[1]")){
            start="9:00"; end="9:50";
            if(now==1 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[2]")){
            if(start.equals("no")) start="10:00";
            end="10:50";
            if(now==2 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[3]")){
            if(start.equals("no")) start="11:00";
            end="11:50";
            if(now==3 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[4]")){
            if(start.equals("no")) start="12:00";
            end="12:50";
            if(now==4 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[5]")){
            if(start.equals("no")) start="13:00";
            end="13:50";
            if(now==5 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[6]")){
            if(start.equals("no")) start="14:00";
            end="14:50";
            if(now==6 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[7]")){
            if(start.equals("no")) start="15:00";
            end="15:50";
            if(now==7 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[8]")){
            if(start.equals("no")) start="16:00";
            end="16:50";
            if(now==8 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[9]")){
            if(start.equals("no")) start="17:00";
            end="17:50";
            if(now==9 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[10]")){
            if(start.equals("no")) start="18:00";
            end="18:50";
            if(now==10 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[11]")){
            if(start.equals("no")) start="19:00";
            end="19:50";
            if(now==11 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[12]")){
            if(start.equals("no")) start="20:00";
            end="20:50";
            if(now==12 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }
        if(subTime.contains("[13]")){
            if(start.equals("no")) start="21:00";
            end="21:50";
            if(now==13 && courseList.get(i).getCourseTime().contains(dayOfWeekString)) {
                courseTitle.setTextColor(Color.parseColor("#c02266"));
                courseTime.setTextColor(Color.parseColor("#c02266"));
                courseRoom.setTextColor(Color.parseColor("#c02266"));
            }
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseTime.setText(start + " ~ " + end);
        start="no";
        courseRoom.setText(courseList.get(i).getCourseRoom());
        v.setTag(courseList.get(i).getCourseID());
        return v;
    }

}

package com.cetime.project0925.Fragment.schedule;

import android.app.AlertDialog;
import android.content.Context;
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
import com.cetime.project0925.*;
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

public class AddCourseAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = HomeActivity.userID;
    private Schedule schedule = new Schedule();
    private List<String> courseIDList;
    private List<Integer> courseIndexList;

    public AddCourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<String>();
        courseIndexList = new ArrayList<Integer>();
        new BackgroundTask().execute();

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
        View v = View.inflate(context, R.layout.add_course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        if(courseList.get(i).getCourseGrade().contains("제한없음") || courseList.get(i).getCourseGrade().equals("") || TextUtils.isEmpty(courseList.get(i).getCourseGrade())){ //null값 못 골라냄...
            courseGrade.setText("전학년");
        }
        else{
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseDivide.setText("분반0" + courseList.get(i).getCourseDivide());
        courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        courseTime.setText(courseList.get(i).getCourseTime() + "");
        courseRoom.setText(courseList.get(i).getCourseRoom());

        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);   // 강의 추가
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = false; //강의추가가능여부
                validate = schedule.validate(courseList.get(i).getCourseTime());
                if(!alreadyIn(courseIndexList, courseList.get(i).getCourseIndex())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                else if(validate == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setMessage("해당 시간에 이미 수업이 등록되어있습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(i).getCourseID());
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    String courseIndexString = String.valueOf(courseList.get(i).getCourseIndex());
                    AddCourseRequest addRequest = new AddCourseRequest(userID, courseList.get(i).getCourseID(), courseIndexString, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(addRequest);
                }
            }

        });
        return v;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://coe516.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //결과값 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼에담아두기
                String temp;    //하나씩 문자열로 읽으며 저장
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");  //한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect(); //연결해제
                return stringBuilder.toString().trim(); //문자열들 반환
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int courseIndex;
                String courseTime;
                String courseID;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getString("courseID");
                    courseTime = object.getString("courseTime");
                    courseIndex = object.getInt("courseIndex");
                    courseIDList.add(courseID);
                    courseIndexList.add(courseIndex);
                    schedule.addSchedule(courseTime);
                    count++;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public boolean alreadyIn(List<Integer> courseIndexList, int item){
        for(int i=0; i<courseIndexList.size(); i++){
            if(courseIndexList.get(i)==item){
                return false;
            }
        }
        return true;
    }
/*
    public boolean alreadyIn(List<String> courseIDList, String item){
        for(int i=0; i<courseIDList.size(); i++){
            if(courseIDList.get(i).equals(item)){
                return false;
            }
        }
        return true;
    }
 */
}

package com.cetime.project0925.Fragment.CEinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.schedule.Course;
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
import java.util.Calendar;

public class ClassroomActivity extends AppCompatActivity {
    private ArrayAdapter dayAdapter;
    private Spinner daySpinner;
    private ListView classListView;
    private ClassroomAdapter classroomAdapter;
    private List<Course> classroomList;
    public String userID = HomeActivity.userID;
    public int userPriority = HomeActivity.userPriority;
    public String courseTitle, courseTime, courseRoom;

    private AlertDialog dialog;

    private String courseYearString = "2019";
    private String courseTermString = "2학기";
    public String dayOfWeekString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        Calendar calendar = Calendar.getInstance();
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);

        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        dayAdapter = ArrayAdapter.createFromResource(ClassroomActivity.this, R.array.classDay, android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(DayOfWeekToInt(dayOfWeek));
        classListView = (ListView) findViewById(R.id.classListView);
        classroomList = new ArrayList<Course>();
        classroomAdapter = new ClassroomAdapter(getApplicationContext(), classroomList, hour, minute, second, dayOfWeekString );
        classListView.setAdapter(classroomAdapter);


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassroomActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 2);  //HomeActivity화면
                ClassroomActivity.this.startActivity(intent);
                finish();
            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DayOfWeekToInt(i+2);
                classroomAdapter.selectedDay = dayOfWeekString;
                new BackgroundTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                new BackgroundTask().execute();
            }
        });

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://coe516.cafe24.com/ClassroomList.php?courseYear=" + URLEncoder.encode(courseYearString, "UTF-8") +
                        "&courseTerm=" + URLEncoder.encode(courseTermString, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //결과값 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼에담아두기
                String temp;    //하나씩 문자열로 읽으며 저장
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");  //한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect(); //연결해제
                return stringBuilder.toString().trim(); //문자열들 반환
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                classroomList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int classCnt=0;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseTitle = object.getString("courseTitle");
                    courseTime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");
                    if(courseRoom.substring(0,2).equals("북5") && courseTime.contains(dayOfWeekString)){
                        Course course = new Course(courseTitle, courseTime, courseRoom);
                        classroomList.add(course);
                        classCnt++;
                    }
                    count++;
                }
                if(classCnt == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClassroomActivity.this);
                    dialog = builder.setMessage("수업이 없는 날입니다 :)")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                classroomAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(ClassroomActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

    public int DayOfWeekToInt(int day){
        int num=0;
        switch (day){
            case 1:
                dayOfWeekString = "일";
                num=0;
                break;
            case 2:
                dayOfWeekString = "월";
               num=0;
                break;
            case 3:
                dayOfWeekString = "화";
                num=1;
                break;
            case 4:
                dayOfWeekString = "수";
                num=2;
                break;
            case 5:
                dayOfWeekString = "목";
                num=3;
                break;
            case 6:
                dayOfWeekString = "금";
                num=4;
                break;
            case 7:
                dayOfWeekString = "토";
                num=0;
                break;
        }
        return num;
    }

}

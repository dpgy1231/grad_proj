package com.cetime.project0925.Fragment.CEinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.board.notice.Notice;
import com.cetime.project0925.Fragment.board.notice.NoticeActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    int year, month, date;
    private List<com.cetime.project0925.Fragment.CEinfo.Calendar> calendarList;
    ListView calendarListView;
    private CalendarListAdapter calendarListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 2);  //HomeActivity화면
                CalendarActivity.this.startActivity(intent);
                finish();
            }
        });

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarListView = (ListView) findViewById(R.id.calenderList);
        calendarList = new ArrayList<com.cetime.project0925.Fragment.CEinfo.Calendar>();
        new BackgroundTask().execute();
        calendarListAdapter = new CalendarListAdapter(getApplicationContext(), year, month, date, calendarList);
        calendarListView.setAdapter(calendarListAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                date = i2;
                calendarListAdapter = new CalendarListAdapter(getApplicationContext(), year, month, date, calendarList);
                calendarListView.setAdapter(calendarListAdapter);
                new BackgroundTask().execute();
            }
        });

    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                String scYearString = String.valueOf(year);
                String scMonthString = String.valueOf(month);
                target = "http://coe516.cafe24.com/CalendarList.php?scYear=" + URLEncoder.encode(scYearString, "UTF-8") +
                        "&scMonth=" + URLEncoder.encode(scMonthString, "UTF-8");

            } catch (Exception e) {
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
                calendarList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int scIndex, scYear, scMonth, scDate=0;
                String scContent;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    scIndex = object.getInt("scIndex");
                    scContent = object.getString("scContent");
                    scYear = object.getInt("scYear");
                    scMonth = object.getInt("scMonth");
                    scDate = object.getInt("scDate");
                    com.cetime.project0925.Fragment.CEinfo.Calendar calendar = new com.cetime.project0925.Fragment.CEinfo.Calendar(scIndex, scContent, scYear, scMonth, scDate);
                    calendarList.add(calendar);

                    count++;
                }
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                    dialog = builder.setMessage("조회된 일정이 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                calendarListAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
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
        Toast.makeText(CalendarActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

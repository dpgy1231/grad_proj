package com.cetime.project0925.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.cetime.project0925.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ScheduleFrag scheduleFrag;
    private BoardFrag boardFrag;
    private RoomInfoFrag roomInfoFrag;
    private ChatFrag chatFrag;
    private MypageFrag mypageFrag;
    public static String userID;
    public static int userPriority;
    public static String userName;
    public static String userPassword;
    public static String userGender;
    public static String userGrade;
    public static String userEmail;
    public static String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userID = getIntent().getStringExtra("userID");
        int fragNum = getIntent().getIntExtra("fragNum", 2);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.icon_schedule:
                        setFrag(0);
                        break;
                    case R.id.icon_board:
                        setFrag(1);
                        break;
                    case R.id.icon_roomInfo:
                        setFrag(2);
                        break;
                    case R.id.icon_chat:
                        setFrag(3);
                        break;
                    case R.id.icon_mypage:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        scheduleFrag = new ScheduleFrag();
        boardFrag = new BoardFrag();
        roomInfoFrag = new RoomInfoFrag();
        chatFrag = new ChatFrag();
        mypageFrag = new MypageFrag();

        setFrag(fragNum);
        new BackgroundTask().execute();
    }


    private void setFrag(int n){    //fragment 교체부분
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch(n){
            case 0:
                fragmentTransaction.replace(R.id.main_frame, scheduleFrag);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.main_frame, boardFrag);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, roomInfoFrag);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame, chatFrag);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentTransaction.replace(R.id.main_frame, mypageFrag);
                fragmentTransaction.commit();
                break;
        }
    }
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(HomeActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
            String target;

            @Override
            protected void onPreExecute() {  //php파일 통해 DB데이터 GET
                try { target = "http://coe516.cafe24.com/UserInfo.php?userID=" + URLEncoder.encode(userID, "UTF-8"); }
                catch (Exception e) { e.printStackTrace(); } }

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
            } //결과값 받기

            @Override
            public void onProgressUpdate(Void... values) {
                super.onProgressUpdate();
            }

            @Override
            public void onPostExecute(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()){
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("userID");
                        userPassword = object.getString("userPassword");
                        userName = object.getString("userName");
                        userGender = object.getString("userGender");
                        userGrade = object.getString("userGrade");
                        userEmail = object.getString("userEmail");
                        userPhone = object.getString("userPhone");
                        userPriority = object.getInt("userPriority");
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }   //결과값 파싱 후 적절한 처리
    }

}

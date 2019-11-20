package com.cetime.project0925.Fragment.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cetime.project0925.Fragment.CEinfo.CalendarActivity;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.LoginActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MypageCheckActivity extends AppCompatActivity {

    private String userID = HomeActivity.userID;
    private String userPassword = HomeActivity.userPassword;
    private String userName = HomeActivity.userName;
    private String userEmail = HomeActivity.userEmail;
    private String userGender = HomeActivity.userGender;
    private String userGrade = HomeActivity.userGrade;
    private String userPhone = HomeActivity.userPhone;
    private int userPriority = HomeActivity.userPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_check);

        userName = getIntent().getStringExtra("userName");
        userGender = getIntent().getStringExtra("userGender");
        userGrade = getIntent().getStringExtra("userGrade");
        userEmail = getIntent().getStringExtra("userEmail");
        userPhone = getIntent().getStringExtra("userPhone");

        TextView idText = findViewById(R.id.idText);
        TextView nameText = findViewById(R.id.nameText);
        TextView emailText = findViewById(R.id.emailText);
        TextView genderText = findViewById(R.id.genderText);
        TextView gradeText = findViewById(R.id.gradeText);
        TextView phoneText = findViewById(R.id.phoneText);

        idText.setText("아이디(학번) : " + userID);
        nameText.setText("이름 : " + userName);
        emailText.setText("이메일 : " + userEmail);
        genderText.setText("성별 : " + userGender);
        gradeText.setText("학년 : " + userGrade);
        phoneText.setText("휴대전화번호 : " + userPhone);


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageCheckActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 4);  //HomeActivity화면
                startActivity(intent);
                finish();
            }
        });

        Button modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageCheckActivity.this, MypageModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://coe516.cafe24.com/UserInfo.php?userID=" + URLEncoder.encode(userID, "UTF-8");
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
        }
    }
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(MypageCheckActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

package com.cetime.project0925;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FindPwActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private String userID;
    private String userPassword;
    private String userPasswordCheck;
    public String userEmail;
    private AlertDialog dialog;
    private boolean validate = false;
    public String getUserID, getUserPassword, getUserPasswordCheck, getUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final EditText pwCheckText = (EditText) findViewById(R.id.pwCheckText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final Button pwChangeButton = (Button) findViewById(R.id.pwChangeButton);

        pwText.setEnabled(false);
        pwText.setBackgroundColor(Color.GRAY);
        pwCheckText.setEnabled(false);
        pwCheckText.setBackgroundColor(Color.GRAY);
        pwChangeButton.setEnabled(false);
        pwChangeButton.setBackgroundColor(Color.GRAY);

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserID = idText.getText().toString();
                if(validate){ return; }
                if(getUserID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("아이디를 제대로 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(!success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                                dialog = builder.setMessage("존재하는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                validate = true;
                                pwText.setEnabled(true);
                                pwCheckText.setEnabled(true);
                                pwChangeButton.setEnabled(true);
                                int myColor = getResources().getColor(R.color.colorPrimary);
                                int myButtonColor = getResources().getColor(R.color.colorPrimaryDark);
                                pwText.setBackgroundColor(myColor);
                                pwCheckText.setBackgroundColor(myColor);
                                pwChangeButton.setBackgroundColor(myButtonColor);
                                new BackgroundTask().execute();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                                dialog = builder.setMessage("없는 계정입니다. 관리자에게 문의해 계정을 생성하세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(getUserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindPwActivity.this);
                queue.add(validateRequest);

            }
        });
        pwChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserID = idText.getText().toString();
                getUserPassword = pwText.getText().toString();
                getUserPasswordCheck = pwCheckText.getText().toString();
                getUserEmail = emailText.getText().toString();

                if(getUserID.equals("") || getUserPassword.equals("") || getUserPasswordCheck.equals("") || getUserEmail.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("빈 칸을 모두 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if (!getUserPassword.equals(getUserPasswordCheck)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("비밀번호와 비밀번호 확인이 동일하지 않습니다. 다시 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else if(userEmail.equals(getUserEmail)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                                    dialog = builder.setMessage("비밀번호를 성공적으로 변경하였습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    FindPwRequest findPwRequest = new FindPwRequest(getUserID, getUserPassword, getUserEmail, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(FindPwActivity.this);
                    queue.add(findPwRequest);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("비밀번호 변경에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://coe516.cafe24.com/UserInfo.php?userID=" + URLEncoder.encode(getUserID, "UTF-8");
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
                    String userPassword = object.getString("userPassword");
                    String userName = object.getString("userName");
                    String userGender = object.getString("userGender");
                    String userGrade = object.getString("userGrade");
                    userEmail = object.getString("userEmail");
                    String userPhone = object.getString("userPhone");
                    int userPriority = object.getInt("userPriority");
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(FindPwActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

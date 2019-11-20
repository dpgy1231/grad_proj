package com.cetime.project0925.Fragment.board.toProfessor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FromStudentActivity extends AppCompatActivity {

    private ListView fromStudentListView;
    private FromStudentListAdapter fromStudentListAdapter;
    private List<FromStudent> fromStudentList;
    private String userName = HomeActivity.userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_student);

        fromStudentListView = (ListView) findViewById(R.id.fromStudentListView);
        fromStudentList = new ArrayList<FromStudent>();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FromStudentActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 1);  //HomeActivity화면
                startActivity(intent);
                finish();
            }
        });

        new BackgroundTask().execute();

        fromStudentListAdapter = new FromStudentListAdapter(getApplicationContext(), fromStudentList);
        fromStudentListView.setAdapter(fromStudentListAdapter);
        fromStudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(FromStudentActivity.this, FromStudentReadActivity.class);  //내용보기로 넘어가기
                intent.putExtra("index", fromStudentList.get(i).getIndex());
                intent.putExtra("tag", fromStudentList.get(i).getTag());
                intent.putExtra("name", fromStudentList.get(i).getName());
                intent.putExtra("title", fromStudentList.get(i).getTitle());
                intent.putExtra("content", fromStudentList.get(i).getContent());
                intent.putExtra("writer", fromStudentList.get(i).getWriter());
                intent.putExtra("date", fromStudentList.get(i).getDate());
                intent.putExtra("time", fromStudentList.get(i).getTime());
                intent.putExtra("studentID", fromStudentList.get(i).getUserID());
                startActivity(intent);
                finish();
            }
        });

        fromStudentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){
                final String fromStudentIndexString = String.valueOf(fromStudentList.get(arg2).getIndex());
                    new AlertDialog.Builder(FromStudentActivity.this)
                            .setTitle("삭제 확인")
                            .setMessage("정말로 삭제하시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { dialog.cancel();}
                            })
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {  Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                fromStudentList.remove(arg2);
                                                fromStudentListAdapter.notifyDataSetChanged();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(FromStudentActivity.this);
                                                AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog2.show();
                                            }
                                        }catch (Exception e){  e.printStackTrace(); }
                                    }
                                };
                                    FromStudentDeleteRequest communityDeleteRequest = new FromStudentDeleteRequest(fromStudentIndexString, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(FromStudentActivity.this);
                                    queue.add(communityDeleteRequest);
                                }
                            }).setCancelable(false).create().show();
                return true;
            }
        });

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                System.out.println(userName);
                target = "http://coe516.cafe24.com/FromStudentList.php?forProName=" + URLEncoder.encode(userName, "UTF-8");
            }catch(Exception e){e.printStackTrace();}
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
                int toProIndex=0;
                String toProTag, toProName, toProTitle, toProContent, toProWriter, toProDate, toProTime, toProUserID;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    toProIndex = object.getInt("forProIndex");
                    toProTag = object.getString("forProTag");
                    toProName = object.getString("forProName");
                    toProTitle = object.getString("forProTitle");
                    toProContent = object.getString("forProContent");
                    if(toProTag.equals("익명")){ toProWriter = toProTag; }    //익명이라면 작성자로 익명이 뜨도록
                    else{ toProWriter = object.getString("forProWriter");}
                    toProDate = object.getString("forProDate");
                    toProTime = object.getString("forProTime");
                    toProUserID = object.getString("forProUserID");
                    FromStudent fromStudent = new FromStudent(toProIndex, toProTag, toProName, toProTitle, toProContent, toProWriter, toProDate, toProTime, toProUserID);
                    fromStudentList.add(fromStudent);
                    fromStudentListAdapter.notifyDataSetChanged();
                    count++;
                }
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
        Toast.makeText(FromStudentActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

package com.cetime.project0925.Fragment.board.community;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private ListView communityListView;
    private CommunityListAdapter communityListAdapter;
    private List<Community> communityList;

    public static List<Community2> community2List;

    public String userID = HomeActivity.userID;
    public int userPriority = HomeActivity.userPriority;
    private AlertDialog dialog;

    public String communityUserID;
    public int communityIndex;

    public int count=0;

    private int heartCnt=0;
    //private int cmtCnt=0;
    private boolean heartValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communityListView = (ListView) findViewById(R.id.communityListView);
        communityList = new ArrayList<Community>();
        community2List = new ArrayList<Community2>();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 1);  //HomeActivity화면
                CommunityActivity.this.startActivity(intent);
                finish();
            }
        });

        FloatingActionButton newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, CommunityAddActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                CommunityActivity.this.startActivity(intent);
                finish();
            }
        });

        new BackgroundTask().execute();

        communityListAdapter = new CommunityListAdapter(getApplicationContext(), communityList);
        communityListView.setAdapter(communityListAdapter);
        communityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(CommunityActivity.this, CommunityReadActivity.class);  //내용보기로 넘어가기
                intent.putExtra("index", communityList.get(i).getIndex());
                intent.putExtra("tag", communityList.get(i).getTag());
                intent.putExtra("title", communityList.get(i).getTitle());
                intent.putExtra("content", communityList.get(i).getContent());
                intent.putExtra("writer", communityList.get(i).getWriter());
                intent.putExtra("date", communityList.get(i).getDate());
                intent.putExtra("time", communityList.get(i).getTime());
                intent.putExtra("count", communityList.get(i).getCount()+1);
                intent.putExtra("communityID", communityList.get(i).getUserID());
                intent.putExtra("file", communityList.get(i).getFile());

                heartCnt=0;
                //cmtCnt=0;
                heartValid=false;
                for(int cnt=0; cnt<community2List.size(); cnt++){
                    if(community2List.get(cnt).getIndex()==communityList.get(i).getIndex()) {
                        heartCnt++;
                        if (userID.equals(community2List.get(cnt).getHeartUserID())) { heartValid = true; }
                    }
                }
                /*
                for(int cnt=0; cnt<community2List.size(); cnt++){
                    if(community2List.get(cnt).getIndex()==communityList.get(i).getIndex()) {
                        if (community2List.get(cnt).getHeartUserID().equals("no")) { }
                        else {
                            heartCnt++;
                            if (userID.equals(community2List.get(cnt).getHeartUserID())) { heartValid = true; }
                        }

                        if(community2List.get(cnt).getCmIndex() == 0){}
                        else{ cmtCnt++;}
                    }
                }
                 */
                intent.putExtra("heartCnt", heartCnt);
                intent.putExtra("heartValid", heartValid);
                //intent.putExtra("cmtCnt", cmtCnt);
                startActivity(intent);
                finish();
            }
        });

        communityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){
                final String communityIndexString = String.valueOf(communityList.get(arg2).getIndex());
                if(communityList.get(arg2).getUserID().equals(userID) || (userPriority==0)) {
                    new AlertDialog.Builder(CommunityActivity.this)
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
                                                communityList.remove(arg2);
                                                communityListAdapter.notifyDataSetChanged();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(CommunityActivity.this);
                                                AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog2.show();
                                            }
                                        }catch (Exception e){  e.printStackTrace(); }
                                    }
                                };
                                CommunityDeleteRequest communityDeleteRequest = new CommunityDeleteRequest(communityIndexString, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(CommunityActivity.this);
                                queue.add(communityDeleteRequest);
                                }
                            }).setCancelable(false).create().show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityActivity.this);
                    dialog = builder.setMessage("접근 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                return true;
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://coe516.cafe24.com/CommunityList.php";
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
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //각 공지사항리스트 저장
                count = 0;
                int communityCount =0;
                String communityTag, communityTitle, communityContent, communityWriter, communityDate, communityTime, communityUserID, communityFile;
                while(count < jsonArray.length()) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    communityIndex = object.getInt("communityIndex");
                    communityTag = object.getString("communityTag");
                    communityTitle = object.getString("communityTitle");
                    communityContent = object.getString("communityContent");
                    if(communityTag.equals("익명")){ communityWriter = communityTag; }    //익명이라면 작성자로 익명이 뜨도록
                    else{ communityWriter = object.getString("communityWriter");}
                    communityDate = object.getString("communityDate");
                    communityTime = object.getString("communityTime");
                    communityCount = object.getInt("communityCount");
                    communityUserID = object.getString("communityUserID");
                    communityFile = object.getString("file");
                    Community community = new Community(communityIndex, communityTag, communityTitle, communityContent, communityWriter, communityDate, communityTime, communityCount, communityUserID, communityFile);
                    communityList.add(community);         //communitylist에 글하나
                    communityListAdapter.notifyDataSetChanged();
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
        Toast.makeText(CommunityActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

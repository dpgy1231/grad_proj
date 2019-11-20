package com.cetime.project0925.Fragment.board.notice;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class NoticeActivity extends AppCompatActivity {

    private ArrayAdapter tagAdapter;
    private Spinner tagSpinner;
    private ListView noticeListView;
    private NoticeListAdapter noticeListAdapter;
    private List<Notice> noticeList;
    //public Notice noticeParam;
    public String userID = HomeActivity.userID;
    public int userPriority = HomeActivity.userPriority;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
        tagAdapter = ArrayAdapter.createFromResource(NoticeActivity.this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        tagSpinner.setSelection(0);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeListAdapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(noticeListAdapter);

        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(NoticeActivity.this, NoticeReadActivity.class);  //내용보기로 넘어가기
                //int index = noticeList.get(i).getIndex();
                //noticeParam = new Notice(noticeList.get(i).getIndex(), noticeList.get(i).getTag(), noticeList.get(i).getTitle(), noticeList.get(i).getContent(), noticeList.get(i).getWriter(), noticeList.get(i).getDate(), noticeList.get(i).getTime(), noticeList.get(i).getCount());

                intent.putExtra("index", noticeList.get(i).getIndex());
                intent.putExtra("tag", noticeList.get(i).getTag());
                intent.putExtra("title", noticeList.get(i).getTitle());
                intent.putExtra("content", noticeList.get(i).getContent());
                intent.putExtra("writer", noticeList.get(i).getWriter());
                intent.putExtra("date", noticeList.get(i).getDate());
                intent.putExtra("time", noticeList.get(i).getTime());
                intent.putExtra("count", noticeList.get(i).getCount()+1);
                intent.putExtra("noticeID", noticeList.get(i).getUserID());
                intent.putExtra("file", noticeList.get(i).getFile());
                intent.putExtra("backNum", 1);
                startActivity(intent);
                finish();
            }
        });
        noticeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){
                final String noticeIndexString = String.valueOf(noticeList.get(arg2).getIndex());
                if(noticeList.get(arg2).getUserID().equals(userID) || (userPriority==0)) {
                    new AlertDialog.Builder(NoticeActivity.this)
                        .setTitle("삭제 확인")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                        })
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noticeList.remove(noticeList.get(arg2));
                                noticeListAdapter.notifyDataSetChanged();
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                                                AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog2.show(); }
                                        }catch (Exception e){  e.printStackTrace(); }
                                    }
                                };
                                NoticeDeleteRequest noticeDeleteRequest = new NoticeDeleteRequest(noticeIndexString, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(NoticeActivity.this);
                                queue.add(noticeDeleteRequest);
                            }
                        })
                        .setCancelable(false).create().show();
                }
                return true;
            }
        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 1);  //HomeActivity화면
                NoticeActivity.this.startActivity(intent);
                finish();
            }
        });

        FloatingActionButton newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPriority == 0){
                    Intent intent = new Intent(NoticeActivity.this, NoticeAddActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    NoticeActivity.this.startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                    dialog = builder.setMessage("게시물을 작성할 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
        protected void onPreExecute(){
            try{
                if(tagSpinner.getSelectedItem().toString().equals("전체")){
                    target = "http://coe516.cafe24.com/BoardList.php";
                }
                else{
                    target = "http://coe516.cafe24.com/NoticeList.php?noticeTag=" + URLEncoder.encode(tagSpinner.getSelectedItem().toString(), "UTF-8");
                }
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
                noticeList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int noticeIndex, noticeCount =0;
                String noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeUserID, noticeFile;
                while(count < jsonArray.length()) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeIndex = object.getInt("noticeIndex");
                    noticeTag = object.getString("noticeTag");
                    noticeTitle = object.getString("noticeTitle");
                    noticeContent = object.getString("noticeContent");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    noticeTime = object.getString("noticeTime");
                    noticeCount = object.getInt("noticeCount");
                    noticeUserID = object.getString("noticeUserID");
                    noticeFile = object.getString("noticeFile");
                    Notice notice = new Notice(noticeIndex, noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeCount, noticeUserID, noticeFile);
                    noticeList.add(notice);         //noticelist에 공지하나
                    count++;
                }
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                    dialog = builder.setMessage("조회된 공지가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                noticeListAdapter.notifyDataSetChanged();
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
        Toast.makeText(NoticeActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

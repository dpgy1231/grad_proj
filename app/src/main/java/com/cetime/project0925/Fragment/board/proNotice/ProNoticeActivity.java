package com.cetime.project0925.Fragment.board.proNotice;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class ProNoticeActivity extends AppCompatActivity {

    private ArrayAdapter tagAdapter;
    private Spinner tagSpinner;
    private ListView proNoticeListView;
    private ProNoticeListAdapter proNoticeListAdapter;
    private List<ProNotice> proNoticeList;
    public String userID = HomeActivity.userID;
    public int userPriority = HomeActivity.userPriority;

    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_notice);
        tagSpinner = (Spinner) findViewById(R.id.tagSpinner);
        tagAdapter = ArrayAdapter.createFromResource(ProNoticeActivity.this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        tagSpinner.setSelection(0);

        proNoticeListView = (ListView) findViewById(R.id.proNoticeListView);
        proNoticeList = new ArrayList<ProNotice>();
        proNoticeListAdapter = new ProNoticeListAdapter(ProNoticeActivity.this, proNoticeList);
        proNoticeListView.setAdapter(proNoticeListAdapter);

        proNoticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(ProNoticeActivity.this, ProNoticeReadActivity.class);  //내용보기로 넘어가기
                intent.putExtra("index", proNoticeList.get(i).getIndex());
                intent.putExtra("tag", proNoticeList.get(i).getTag());
                intent.putExtra("title", proNoticeList.get(i).getTitle());
                intent.putExtra("content", proNoticeList.get(i).getContent());
                intent.putExtra("writer", proNoticeList.get(i).getWriter());
                intent.putExtra("date", proNoticeList.get(i).getDate());
                intent.putExtra("time", proNoticeList.get(i).getTime());
                intent.putExtra("count", proNoticeList.get(i).getCount()+1);
                intent.putExtra("proID", proNoticeList.get(i).getUserID());
                intent.putExtra("file", proNoticeList.get(i).getFile());
                startActivity(intent);
                finish();
            }
        });
        proNoticeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){
                final String proNoticeIndexString = String.valueOf(proNoticeList.get(arg2).getIndex());
                if(proNoticeList.get(arg2).getUserID().equals(userID) || (userPriority==0)) {
                    new AlertDialog.Builder(ProNoticeActivity.this)
                            .setTitle("삭제 확인")
                            .setMessage("정말로 삭제하시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                            })
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    proNoticeList.remove(proNoticeList.get(arg2));
                                    proNoticeListAdapter.notifyDataSetChanged();
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                if (success) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeActivity.this);
                                                    AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                            .setNegativeButton("확인", null)
                                                            .create();
                                                    dialog2.show();
                                                }
                                            }catch (Exception e){  e.printStackTrace(); }
                                        }
                                    };
                                    ProNoticeDeleteRequest proNoticeDeleteRequest = new ProNoticeDeleteRequest(proNoticeIndexString, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(ProNoticeActivity.this);
                                    queue.add(proNoticeDeleteRequest);
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
                Intent intent = new Intent(ProNoticeActivity.this, HomeActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                intent.putExtra("fragNum", 1);  //HomeActivity화면
                ProNoticeActivity.this.startActivity(intent);
                finish();
            }
        });

        FloatingActionButton newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPriority == 0 || userPriority==2){ //0:관리자 2:교수님
                    Intent intent = new Intent(ProNoticeActivity.this, ProNoticeAddActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    ProNoticeActivity.this.startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeActivity.this);
                    dialog = builder.setMessage("게시물을 작성할 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { new BackgroundTask().execute(); }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { new BackgroundTask().execute(); }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                if(tagSpinner.getSelectedItem().toString().equals("전체")){
                    target = "http://coe516.cafe24.com/ProBoardList.php";
                }
                else{
                    target = "https://coe516.cafe24.com/ProNoticeList.php?proTag=" + URLEncoder.encode(tagSpinner.getSelectedItem().toString(), "UTF-8");
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
                proNoticeList.clear();
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //각 공지사항리스트 저장
                int count = 0;
                int proIndex, proCount =0;
                String proTag, proTitle, proContent, proWriter, proDate, proTime, proUserID, proFile;
                while(count < jsonArray.length()) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    proIndex = object.getInt("proIndex");
                    proTag = object.getString("proTag");
                    proTitle = object.getString("proTitle");
                    proContent = object.getString("proContent");
                    proWriter = object.getString("proWriter");
                    proDate = object.getString("proDate");
                    proTime = object.getString("proTime");
                    proCount = object.getInt("proCount");
                    proUserID = object.getString("proUserID");
                    proFile = object.getString("proFile");
                    ProNotice proNotice = new ProNotice(proIndex, proTag, proTitle, proContent, proWriter, proDate, proTime, proCount, proUserID, proFile);
                    proNoticeList.add(proNotice);         //proNoticelist에 공지하나
                    count++;
                }
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeActivity.this);
                    dialog = builder.setMessage("조회된 공지가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                proNoticeListAdapter.notifyDataSetChanged();
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
        Toast.makeText(ProNoticeActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

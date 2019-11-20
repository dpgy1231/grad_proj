package com.cetime.project0925.Fragment.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.mypage.User;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ChatFindActivity extends AppCompatActivity {
    private ListView userListView;
    private UserListAdapter userListAdapter;
    public List<User> userList;

    private String yourID, yourName;
    private ChatListAdapter chatListAdapter;
    private String userID = HomeActivity.userID;
    private List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_find);

        Intent intent = getIntent();
        yourID = intent.getStringExtra("yourID");
        yourName = intent.getStringExtra("yourName");
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        chatList = new ArrayList<Chat>();
        chatList = (List<Chat>) bundle.getSerializable("chatList");

        userListView = (ListView) findViewById(R.id.findListView);
        userList = new ArrayList<User>();
        userListAdapter = new UserListAdapter(ChatFindActivity.this, userList);
        userListView.setAdapter(userListAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(ChatFindActivity.this, ChatRoomActivity.class);
                intent.putExtra("yourID", userList.get(i).getUserID());
                intent.putExtra("yourName", userList.get(i).getName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("chatList", (Serializable)chatList);
                intent.putExtra("BUNDLE",bundle);
                intent.putExtra("fragNum", 3);  //ChatFrag
                startActivity(intent);
                finish();
            }
        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatFindActivity.this, HomeActivity.class);
                intent.putExtra("fragNum", 3);  //HomeActivity화면
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });
        new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://coe516.cafe24.com/UserList.php?userID=" + URLEncoder.encode(HomeActivity.userID, "UTF-8");
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
                userList.clear();
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String userID, userName;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userID = object.getString("userID");
                    userName = object.getString("userName");
                    User user = new User(userID, userName);
                    userList.add(user);
                    count++;
                }
                userListAdapter.notifyDataSetChanged();
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
        Toast.makeText(ChatFindActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

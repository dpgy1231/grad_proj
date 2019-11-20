package com.cetime.project0925.Fragment.chat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private String yourID, yourName;
    private ChatListAdapter chatListAdapter;
    private String userID = HomeActivity.userID;
    private String userName = HomeActivity.userName;
    private List<Chat> chatList;
    private List<Chat> yourChatList;
    private ListView chatListView;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");
    private AlertDialog dialog;
    private int index;
    private int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        yourID = intent.getStringExtra("yourID");
        yourName = intent.getStringExtra("yourName");
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        chatList = (List<Chat>) bundle.getSerializable("chatList");
        yourChatList = new ArrayList<Chat>();

        int chatIndex=0, chatCount=0;
        String chatID1, chatName1, chatID2, chatName2, chatContent, chatDate, chatTime;
        for(int i=0; i<chatList.size(); i++){
            if(chatList.get(i).getID1().equals(yourID) || chatList.get(i).getID2().equals(yourID)){
                chatIndex = chatList.get(i).getIndex();
                chatID1 = chatList.get(i).getID1();
                chatName1 = chatList.get(i).getName1();
                chatID2 = chatList.get(i).getID2();
                chatName2 = chatList.get(i).getName2();
                chatContent = chatList.get(i).getContent();
                chatDate = chatList.get(i).getDate();
                chatTime = chatList.get(i).getTime();
                chatCount = chatList.get(i).getCount();
                Chat chat = new Chat(chatIndex, chatID1, chatName1, chatID2, chatName2, chatContent, chatDate, chatTime, chatCount);
                yourChatList.add(chat);
            }
            index = chatList.get(i).getIndex();
        }

        TextView yourNameText = (TextView) findViewById(R.id.yourNameText);
        yourNameText.setText(yourName);

        chatListView = (ListView) findViewById(R.id.chatListView);
        chatListAdapter = new ChatListAdapter(ChatRoomActivity.this, yourChatList, yourID);
        chatListView.setAdapter(chatListAdapter);
        scrollMyListViewToBottom();

        chatListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){  //댓글삭제
                final String chatIndexString = String.valueOf(yourChatList.get(arg2).getIndex());
                    new AlertDialog.Builder(ChatRoomActivity.this)
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
                                                System.out.println(yourChatList.get(arg2).getIndex());
                                                yourChatList.remove(arg2);
                                                chatListAdapter.notifyDataSetChanged();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                                                AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog2.show();
                                            }
                                        }catch (Exception e){  e.printStackTrace(); }
                                    }
                                };
                                ChatDeleteRequest chatDeleteRequest = new ChatDeleteRequest(chatIndexString, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);
                                queue.add(chatDeleteRequest);
                                }
                            }).setCancelable(false).create().show();
                return true;
            }
        });

        final EditText chatContentEdit = (EditText) findViewById(R.id.chatContent);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String chatContentText = chatContentEdit.getText().toString();
                final String chatDateText = mFormat.format(System.currentTimeMillis());
                final String chatTimeText = mFormatTime.format(System.currentTimeMillis());

                if (chatContentText.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Chat chat = new Chat(temp--, userID, userName, yourID, yourName, chatContentText, chatDateText, chatTimeText, 0);
                                chatList.add(chat);
                                yourChatList.add(chat);
                                chatContentEdit.setText("");
                                chatListAdapter.notifyDataSetChanged();
                            } else { }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ChatAddRequest chatAddRequest = new ChatAddRequest( userID, userName, yourID, yourName, chatContentText, chatDateText, chatTimeText, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChatRoomActivity.this);
                queue.add(chatAddRequest);
            }
        });



        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatRoomActivity.this, HomeActivity.class);
                intent.putExtra("fragNum", 3);  //HomeActivity화면
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });
    }
    private void scrollMyListViewToBottom() {
        chatListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                chatListView.setSelection(chatListAdapter.getCount() - 1);
            }
        });

    }
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(ChatRoomActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

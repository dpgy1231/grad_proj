package com.cetime.project0925.Fragment.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.board.community.Comment;
import com.cetime.project0925.Fragment.board.community.CommunityReadActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class ChatRoomListAdapter extends BaseAdapter {
    private Context context;
    private List<ChatRoom> chatRoomList;
    private Fragment parent;
    private ChatRoom chatRoom = new ChatRoom();
    private String userID = HomeActivity.userID;

    public ChatRoomListAdapter(Context context, List<ChatRoom> chatRoomList) {
        this.context = context;
        this.chatRoomList = chatRoomList;
        chatRoom = new ChatRoom();
    }

    public ChatRoomListAdapter(Context context, List<ChatRoom> chatRoomList, Fragment parent) {
        this.context = context;
        this.chatRoomList = chatRoomList;
        this.parent = parent;
        chatRoom = new ChatRoom();
    }

    @Override
    public int getCount() {
        return chatRoomList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatRoomList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_chat_room, null);
        TextView chatYourName = (TextView) v.findViewById(R.id.yourName);
        TextView chatYourID = (TextView) v.findViewById(R.id.yourID);
        TextView chatContent = (TextView) v.findViewById(R.id.chatContent);
        TextView chatDate = (TextView) v.findViewById(R.id.chatDate);
        TextView chatTime = (TextView) v.findViewById(R.id.chatTime);

        chatYourName.setText(chatRoomList.get(i).getYourName());
        chatYourID.setText(" [" + chatRoomList.get(i).getYourID() +"]");
        chatContent.setText(chatRoomList.get(i).getContent());
        chatDate.setText(chatRoomList.get(i).getDate());
        chatTime.setText(chatRoomList.get(i).getTime());

        v.setTag(chatRoomList.get(i).getIndex());
        return v;
    }

}

package com.cetime.project0925.Fragment.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {
    private Context context;
    private List<Chat> chatList;
    private String userID = HomeActivity.userID;
    private Chat chat;
    private String yourID;

    public ChatListAdapter(Context context, List<Chat> chatList, String yourID) {
        this.context = context;
        this.chatList = chatList;
        chat = new Chat();
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_chat, null);

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.layout);
        TextView chatContent = (TextView) v.findViewById(R.id.chatContent);
        TextView chatDate = (TextView) v.findViewById(R.id.chatDate);
        TextView chatTime = (TextView) v.findViewById(R.id.chatTime);
        TextView chatCheck = (TextView) v.findViewById(R.id.chatCheck);

        chatContent.setText(chatList.get(i).getContent());

        String chatDateString = chatList.get(i).getDate();
        String chatTimeString = chatList.get(i).getTime();
        chatDate.setText(chatDateString.substring(5, 7) + "/" + chatDateString.substring(8, 10));
        chatTime.setText(chatTimeString.substring(0, 2) + ":" + chatTimeString.substring(3, 5));
/*
        if(chatList.get(i).getCount()==0) {chatCheck.setText("읽지않음");}
        else{ chatCheck.setText("읽음"); }
*/
        if(userID.equals(chatList.get(i).getID1())){
            layout.setGravity(Gravity.RIGHT);
            chatCheck.setVisibility(View.INVISIBLE);
        }
        else{
            layout.setGravity(Gravity.LEFT);
            chatCheck.setText("");
            chatCheck.setVisibility(View.INVISIBLE);
        }

        v.setTag(chatList.get(i).getIndex());
        return v;
    }
    public void remove(int position){
        chatList.remove(position);
    }
}

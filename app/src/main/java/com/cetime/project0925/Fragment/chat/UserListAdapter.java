package com.cetime.project0925.Fragment.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.chat.ChatRoom;
import com.cetime.project0925.Fragment.mypage.User;
import com.cetime.project0925.R;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private String userID = HomeActivity.userID;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_chat_find, null);
        TextView userIDText = (TextView) v.findViewById(R.id.userID);
        TextView userNameText = (TextView) v.findViewById(R.id.userName);

        userIDText.setText(userList.get(i).getUserID());
        userNameText.setText(userList.get(i).getName());

        v.setTag(userList.get(i).getUserID());
        return v;
    }

}

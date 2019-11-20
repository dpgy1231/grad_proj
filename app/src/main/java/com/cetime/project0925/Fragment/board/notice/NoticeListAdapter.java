package com.cetime.project0925.Fragment.board.notice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> noticeList;
    private Fragment parent;
    private Notice notice = new Notice();
    private String userID = HomeActivity.userID;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
        notice = new Notice();
    }

    public NoticeListAdapter(Context context, List<Notice> noticeList, Fragment parent) {
        this.context = context;
        this.noticeList = noticeList;
        this.parent = parent;
        notice = new Notice();
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_notice, null);
        TextView noticeTag = (TextView) v.findViewById(R.id.noticeTag);
        TextView noticeTitle = (TextView) v.findViewById(R.id.noticeTitle);
        TextView noticeWriter = (TextView) v.findViewById(R.id.noticeWriter);
        TextView noticeDate = (TextView) v.findViewById(R.id.noticeDate);
        TextView noticeTime = (TextView) v.findViewById(R.id.noticeTime);

        noticeTag.setText(" [" + noticeList.get(i).getTag() +"]");
        noticeTitle.setText(noticeList.get(i).getTitle());
        noticeWriter.setText(noticeList.get(i).getWriter());
        noticeDate.setText(noticeList.get(i).getDate());
        noticeTime.setText(noticeList.get(i).getTime());

        v.setTag(noticeList.get(i).getTitle());
        return v;
    }
}

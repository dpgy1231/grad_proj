package com.cetime.project0925.Fragment.board.proNotice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cetime.project0925.R;

import java.util.List;

public class ProNoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<ProNotice> proNoticeList;

    public ProNoticeListAdapter(Context context, List<ProNotice> proNoticeList) {
        this.context = context;
        this.proNoticeList = proNoticeList;
    }

    @Override
    public int getCount() {
        return proNoticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return proNoticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_pro_notice, null);
        TextView proTag = (TextView) v.findViewById(R.id.proTag);
        TextView proTitle = (TextView) v.findViewById(R.id.proTitle);
        TextView proWriter = (TextView) v.findViewById(R.id.proWriter);
        TextView proDate = (TextView) v.findViewById(R.id.proDate);
        TextView proTime = (TextView) v.findViewById(R.id.proTime);

        proTag.setText(" [" + proNoticeList.get(i).getTag() +"]");
        proTitle.setText(proNoticeList.get(i).getTitle());
        proWriter.setText(proNoticeList.get(i).getWriter());
        proDate.setText(proNoticeList.get(i).getDate());
        proTime.setText(proNoticeList.get(i).getTime());

        v.setTag(proNoticeList.get(i).getTitle());
        return v;
    }


}

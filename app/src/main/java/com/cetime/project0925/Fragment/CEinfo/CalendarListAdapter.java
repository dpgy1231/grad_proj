package com.cetime.project0925.Fragment.CEinfo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.board.notice.Notice;
import com.cetime.project0925.R;

import java.util.List;

public class CalendarListAdapter extends BaseAdapter {
    private Context context;
    private List<Calendar> calendarList;
    private Calendar calendar = new Calendar();
    private int year,month,date;

    public CalendarListAdapter(Context context, int year, int month, int date, List<Calendar> calendarList) {
        this.context = context;
        this.calendarList = calendarList;
        calendar = new Calendar();
        this.year = year;
        this.month = month;
        this.date = date;
    }

    @Override
    public int getCount() {
        return calendarList.size();
    }

    @Override
    public Object getItem(int i) {
        return calendarList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_calendar, null);
        TextView content = (TextView) v.findViewById(R.id.day_content);

        content.setText(calendarList.get(i).getDate() + "일 : " + calendarList.get(i).getContent());
        if(calendarList.get(i).getContent().contains("(휴일)")){
            content.setTextColor(Color.parseColor("#c02266"));
        }
        v.setTag(calendarList.get(i).getIndex());
        return v;
    }


}

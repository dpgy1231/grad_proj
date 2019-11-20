package com.cetime.project0925.Fragment.board.toProfessor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.Fragment.mypage.User;
import com.cetime.project0925.R;

import java.util.List;

public class ProfessorListAdapter extends BaseAdapter {
    private Context context;
    private List<String> professorList;

    public ProfessorListAdapter(Context context, List<String> professorList) {
        this.context = context;
        this.professorList = professorList;
    }

    @Override
    public int getCount() {
        return professorList.size();
    }

    @Override
    public Object getItem(int i) {
        return professorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_professor, null);
        TextView userNameText = (TextView) v.findViewById(R.id.userName);
        userNameText.setText(professorList.get(i));

        v.setTag(professorList.get(i));
        return v;
    }

}

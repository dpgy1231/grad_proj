package com.cetime.project0925.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.CEinfo.CalendarActivity;
import com.cetime.project0925.Fragment.CEinfo.ClassroomActivity;
import com.cetime.project0925.R;

public class RoomInfoFrag extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_roominfo, container, false);

        return view;
    }

    private String userID = HomeActivity.userID;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final ImageButton calendarButton = (ImageButton) view.findViewById(R.id.calendar);
        final ImageButton classRoomButton = (ImageButton) view.findViewById(R.id.classroom);
        final ImageButton preparationButton = (ImageButton) view.findViewById(R.id.preparation);
        final ImageButton officeButton = (ImageButton) view.findViewById(R.id.office);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        classRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClassroomActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        preparationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dialog = builder.setMessage("채팅 탭에서 준비실 조교 \n'강준영[2015305003]', '홍송희[2015305084]'\n를 찾아 질문을 보내주세요! ;)")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
            }
        });

        officeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dialog = builder.setMessage("채팅 탭에서 학과조교 \n'관리자[admin]'\n을 찾아 질문을 보내주세요! ;)")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
            }
        });
    }
}

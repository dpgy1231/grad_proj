package com.cetime.project0925.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.mypage.MypageCheckActivity;
import com.cetime.project0925.Fragment.mypage.MypageRequest;
import com.cetime.project0925.LoginActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

public class MypageFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ScheduleFrag.OnFragmentInteractionListener mListener;

    public MypageFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageFrag newInstance(String param1, String param2) {
        MypageFrag fragment = new MypageFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);
        return view;
    }

    private View view;
    private AlertDialog dialog;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final TextView logoutButton = (TextView) view.findViewById(R.id.logoutButton);
        final EditText pwText = (EditText) view.findViewById(R.id.pwText);
        final Button checkButton = (Button) view.findViewById(R.id.checkButton);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwText.getText().toString().equals(HomeActivity.userPassword)){
                    Intent intent = new Intent(getActivity(), MypageCheckActivity.class);
                    intent.putExtra("userName", HomeActivity.userName);
                    intent.putExtra("userGender", HomeActivity.userGender);
                    intent.putExtra("userGrade", HomeActivity.userGrade);
                    intent.putExtra("userEmail", HomeActivity.userEmail);
                    intent.putExtra("userPhone", HomeActivity.userPhone);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("로그인에 실패하였습니다. 입력한 정보를 다시 확인하세요.")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    dialog.show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    @Override
    public void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}

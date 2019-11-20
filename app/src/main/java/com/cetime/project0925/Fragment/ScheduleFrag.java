package com.cetime.project0925.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.schedule.AddCourseActivity;
import com.cetime.project0925.Fragment.schedule.DeleteCourseActivity;
import com.cetime.project0925.Fragment.schedule.Schedule;
import com.cetime.project0925.R;
import com.cetime.project0925.reference.AutoResizeTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ScheduleFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScheduleFrag() {
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
    public static ScheduleFrag newInstance(String param1, String param2) {
        ScheduleFrag fragment = new ScheduleFrag();
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
        view = inflater.inflate(R.layout.frag_schedule, container, false);

        return view;
    }

    private AutoResizeTextView monday[] = new AutoResizeTextView[37];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[37];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[37];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[37];
    private AutoResizeTextView friday[] = new AutoResizeTextView[37];
    private Schedule schedule = new Schedule();
    private View view;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        Button addButton = (Button)view.findViewById(R.id.addButton);
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeleteCourseActivity.class);
                intent.putExtra("userID", HomeActivity.userID);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        monday[1] = (AutoResizeTextView) getView().findViewById(R.id.monday9_1);
        monday[2] = (AutoResizeTextView) getView().findViewById(R.id.monday9_2);
        monday[3] = (AutoResizeTextView) getView().findViewById(R.id.monday9_3);
        monday[4] = (AutoResizeTextView) getView().findViewById(R.id.monday9_4);
        monday[5] = (AutoResizeTextView) getView().findViewById(R.id.monday10_1);
        monday[6] = (AutoResizeTextView) getView().findViewById(R.id.monday10_2);
        monday[7] = (AutoResizeTextView) getView().findViewById(R.id.monday10_3);
        monday[8] = (AutoResizeTextView) getView().findViewById(R.id.monday10_4);
        monday[9] = (AutoResizeTextView) getView().findViewById(R.id.monday11_1);
        monday[10] = (AutoResizeTextView) getView().findViewById(R.id.monday11_2);
        monday[11] = (AutoResizeTextView) getView().findViewById(R.id.monday11_3);
        monday[12] = (AutoResizeTextView) getView().findViewById(R.id.monday11_4);
        monday[13] = (AutoResizeTextView) getView().findViewById(R.id.monday12_1);
        monday[14] = (AutoResizeTextView) getView().findViewById(R.id.monday12_2);
        monday[15] = (AutoResizeTextView) getView().findViewById(R.id.monday12_3);
        monday[16] = (AutoResizeTextView) getView().findViewById(R.id.monday12_4);
        monday[17] = (AutoResizeTextView) getView().findViewById(R.id.monday13_1);
        monday[18] = (AutoResizeTextView) getView().findViewById(R.id.monday13_2);
        monday[19] = (AutoResizeTextView) getView().findViewById(R.id.monday13_3);
        monday[20] = (AutoResizeTextView) getView().findViewById(R.id.monday13_4);
        monday[21] = (AutoResizeTextView) getView().findViewById(R.id.monday14_1);
        monday[22] = (AutoResizeTextView) getView().findViewById(R.id.monday14_2);
        monday[23] = (AutoResizeTextView) getView().findViewById(R.id.monday14_3);
        monday[24] = (AutoResizeTextView) getView().findViewById(R.id.monday14_4);
        monday[25] = (AutoResizeTextView) getView().findViewById(R.id.monday15_1);
        monday[26] = (AutoResizeTextView) getView().findViewById(R.id.monday15_2);
        monday[27] = (AutoResizeTextView) getView().findViewById(R.id.monday15_3);
        monday[28] = (AutoResizeTextView) getView().findViewById(R.id.monday15_4);
        monday[29] = (AutoResizeTextView) getView().findViewById(R.id.monday16_1);
        monday[30] = (AutoResizeTextView) getView().findViewById(R.id.monday16_2);
        monday[31] = (AutoResizeTextView) getView().findViewById(R.id.monday16_3);
        monday[32] = (AutoResizeTextView) getView().findViewById(R.id.monday16_4);
        monday[33] = (AutoResizeTextView) getView().findViewById(R.id.monday17_1);
        monday[34] = (AutoResizeTextView) getView().findViewById(R.id.monday17_2);
        monday[35] = (AutoResizeTextView) getView().findViewById(R.id.monday17_3);
        monday[36] = (AutoResizeTextView) getView().findViewById(R.id.monday17_4);

        tuesday[1] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9_1);
        tuesday[2] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9_2);
        tuesday[3] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9_3);
        tuesday[4] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9_4);
        tuesday[5] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10_1);
        tuesday[6] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10_2);
        tuesday[7] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10_3);
        tuesday[8] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10_4);
        tuesday[9] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11_1);
        tuesday[10] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11_2);
        tuesday[11] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11_3);
        tuesday[12] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11_4);
        tuesday[13] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12_1);
        tuesday[14] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12_2);
        tuesday[15] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12_3);
        tuesday[16] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12_4);
        tuesday[17] = (AutoResizeTextView) getView().findViewById(R.id.tuesday13_1);
        tuesday[18] = (AutoResizeTextView) getView().findViewById(R.id.tuesday13_2);
        tuesday[19] = (AutoResizeTextView) getView().findViewById(R.id.tuesday13_3);
        tuesday[20] = (AutoResizeTextView) getView().findViewById(R.id.tuesday13_4);
        tuesday[21] = (AutoResizeTextView) getView().findViewById(R.id.tuesday14_1);
        tuesday[22] = (AutoResizeTextView) getView().findViewById(R.id.tuesday14_2);
        tuesday[23] = (AutoResizeTextView) getView().findViewById(R.id.tuesday14_3);
        tuesday[24] = (AutoResizeTextView) getView().findViewById(R.id.tuesday14_4);
        tuesday[25] = (AutoResizeTextView) getView().findViewById(R.id.tuesday15_1);
        tuesday[26] = (AutoResizeTextView) getView().findViewById(R.id.tuesday15_2);
        tuesday[27] = (AutoResizeTextView) getView().findViewById(R.id.tuesday15_3);
        tuesday[28] = (AutoResizeTextView) getView().findViewById(R.id.tuesday15_4);
        tuesday[29] = (AutoResizeTextView) getView().findViewById(R.id.tuesday16_1);
        tuesday[30] = (AutoResizeTextView) getView().findViewById(R.id.tuesday16_2);
        tuesday[31] = (AutoResizeTextView) getView().findViewById(R.id.tuesday16_3);
        tuesday[32] = (AutoResizeTextView) getView().findViewById(R.id.tuesday16_4);
        tuesday[33] = (AutoResizeTextView) getView().findViewById(R.id.tuesday17_1);
        tuesday[34] = (AutoResizeTextView) getView().findViewById(R.id.tuesday17_2);
        tuesday[35] = (AutoResizeTextView) getView().findViewById(R.id.tuesday17_3);
        tuesday[36] = (AutoResizeTextView) getView().findViewById(R.id.tuesday17_4);

        wednesday[1] = (AutoResizeTextView) getView().findViewById(R.id.wednesday9_1);
        wednesday[2] = (AutoResizeTextView) getView().findViewById(R.id.wednesday9_2);
        wednesday[3] = (AutoResizeTextView) getView().findViewById(R.id.wednesday9_3);
        wednesday[4] = (AutoResizeTextView) getView().findViewById(R.id.wednesday9_4);
        wednesday[5] = (AutoResizeTextView) getView().findViewById(R.id.wednesday10_1);
        wednesday[6] = (AutoResizeTextView) getView().findViewById(R.id.wednesday10_2);
        wednesday[7] = (AutoResizeTextView) getView().findViewById(R.id.wednesday10_3);
        wednesday[8] = (AutoResizeTextView) getView().findViewById(R.id.wednesday10_4);
        wednesday[9] = (AutoResizeTextView) getView().findViewById(R.id.wednesday11_1);
        wednesday[10] = (AutoResizeTextView) getView().findViewById(R.id.wednesday11_2);
        wednesday[11] = (AutoResizeTextView) getView().findViewById(R.id.wednesday11_3);
        wednesday[12] = (AutoResizeTextView) getView().findViewById(R.id.wednesday11_4);
        wednesday[13] = (AutoResizeTextView) getView().findViewById(R.id.wednesday12_1);
        wednesday[14] = (AutoResizeTextView) getView().findViewById(R.id.wednesday12_2);
        wednesday[15] = (AutoResizeTextView) getView().findViewById(R.id.wednesday12_3);
        wednesday[16] = (AutoResizeTextView) getView().findViewById(R.id.wednesday12_4);
        wednesday[17] = (AutoResizeTextView) getView().findViewById(R.id.wednesday13_1);
        wednesday[18] = (AutoResizeTextView) getView().findViewById(R.id.wednesday13_2);
        wednesday[19] = (AutoResizeTextView) getView().findViewById(R.id.wednesday13_3);
        wednesday[20] = (AutoResizeTextView) getView().findViewById(R.id.wednesday13_4);
        wednesday[21] = (AutoResizeTextView) getView().findViewById(R.id.wednesday14_1);
        wednesday[22] = (AutoResizeTextView) getView().findViewById(R.id.wednesday14_2);
        wednesday[23] = (AutoResizeTextView) getView().findViewById(R.id.wednesday14_3);
        wednesday[24] = (AutoResizeTextView) getView().findViewById(R.id.wednesday14_4);
        wednesday[25] = (AutoResizeTextView) getView().findViewById(R.id.wednesday15_1);
        wednesday[26] = (AutoResizeTextView) getView().findViewById(R.id.wednesday15_2);
        wednesday[27] = (AutoResizeTextView) getView().findViewById(R.id.wednesday15_3);
        wednesday[28] = (AutoResizeTextView) getView().findViewById(R.id.wednesday15_4);
        wednesday[29] = (AutoResizeTextView) getView().findViewById(R.id.wednesday16_1);
        wednesday[30] = (AutoResizeTextView) getView().findViewById(R.id.wednesday16_2);
        wednesday[31] = (AutoResizeTextView) getView().findViewById(R.id.wednesday16_3);
        wednesday[32] = (AutoResizeTextView) getView().findViewById(R.id.wednesday16_4);
        wednesday[33] = (AutoResizeTextView) getView().findViewById(R.id.wednesday17_1);
        wednesday[34] = (AutoResizeTextView) getView().findViewById(R.id.wednesday17_2);
        wednesday[35] = (AutoResizeTextView) getView().findViewById(R.id.wednesday17_3);
        wednesday[36] = (AutoResizeTextView) getView().findViewById(R.id.wednesday17_4);

        thursday[1] = (AutoResizeTextView) getView().findViewById(R.id.thursday9_1);
        thursday[2] = (AutoResizeTextView) getView().findViewById(R.id.thursday9_2);
        thursday[3] = (AutoResizeTextView) getView().findViewById(R.id.thursday9_3);
        thursday[4] = (AutoResizeTextView) getView().findViewById(R.id.thursday9_4);
        thursday[5] = (AutoResizeTextView) getView().findViewById(R.id.thursday10_1);
        thursday[6] = (AutoResizeTextView) getView().findViewById(R.id.thursday10_2);
        thursday[7] = (AutoResizeTextView) getView().findViewById(R.id.thursday10_3);
        thursday[8] = (AutoResizeTextView) getView().findViewById(R.id.thursday10_4);
        thursday[9] = (AutoResizeTextView) getView().findViewById(R.id.thursday11_1);
        thursday[10] = (AutoResizeTextView) getView().findViewById(R.id.thursday11_2);
        thursday[11] = (AutoResizeTextView) getView().findViewById(R.id.thursday11_3);
        thursday[12] = (AutoResizeTextView) getView().findViewById(R.id.thursday11_4);
        thursday[13] = (AutoResizeTextView) getView().findViewById(R.id.thursday12_1);
        thursday[14] = (AutoResizeTextView) getView().findViewById(R.id.thursday12_2);
        thursday[15] = (AutoResizeTextView) getView().findViewById(R.id.thursday12_3);
        thursday[16] = (AutoResizeTextView) getView().findViewById(R.id.thursday12_4);
        thursday[17] = (AutoResizeTextView) getView().findViewById(R.id.thursday13_1);
        thursday[18] = (AutoResizeTextView) getView().findViewById(R.id.thursday13_2);
        thursday[19] = (AutoResizeTextView) getView().findViewById(R.id.thursday13_3);
        thursday[20] = (AutoResizeTextView) getView().findViewById(R.id.thursday13_4);
        thursday[21] = (AutoResizeTextView) getView().findViewById(R.id.thursday14_1);
        thursday[22] = (AutoResizeTextView) getView().findViewById(R.id.thursday14_2);
        thursday[23] = (AutoResizeTextView) getView().findViewById(R.id.thursday14_3);
        thursday[24] = (AutoResizeTextView) getView().findViewById(R.id.thursday14_4);
        thursday[25] = (AutoResizeTextView) getView().findViewById(R.id.thursday15_1);
        thursday[26] = (AutoResizeTextView) getView().findViewById(R.id.thursday15_2);
        thursday[27] = (AutoResizeTextView) getView().findViewById(R.id.thursday15_3);
        thursday[28] = (AutoResizeTextView) getView().findViewById(R.id.thursday15_4);
        thursday[29] = (AutoResizeTextView) getView().findViewById(R.id.thursday16_1);
        thursday[30] = (AutoResizeTextView) getView().findViewById(R.id.thursday16_2);
        thursday[31] = (AutoResizeTextView) getView().findViewById(R.id.thursday16_3);
        thursday[32] = (AutoResizeTextView) getView().findViewById(R.id.thursday16_4);
        thursday[33] = (AutoResizeTextView) getView().findViewById(R.id.thursday17_1);
        thursday[34] = (AutoResizeTextView) getView().findViewById(R.id.thursday17_2);
        thursday[35] = (AutoResizeTextView) getView().findViewById(R.id.thursday17_3);
        thursday[36] = (AutoResizeTextView) getView().findViewById(R.id.thursday17_4);

        friday[1] = (AutoResizeTextView) getView().findViewById(R.id.friday9_1);
        friday[2] = (AutoResizeTextView) getView().findViewById(R.id.friday9_2);
        friday[3] = (AutoResizeTextView) getView().findViewById(R.id.friday9_3);
        friday[4] = (AutoResizeTextView) getView().findViewById(R.id.friday9_4);
        friday[5] = (AutoResizeTextView) getView().findViewById(R.id.friday10_1);
        friday[6] = (AutoResizeTextView) getView().findViewById(R.id.friday10_2);
        friday[7] = (AutoResizeTextView) getView().findViewById(R.id.friday10_3);
        friday[8] = (AutoResizeTextView) getView().findViewById(R.id.friday10_4);
        friday[9] = (AutoResizeTextView) getView().findViewById(R.id.friday11_1);
        friday[10] = (AutoResizeTextView) getView().findViewById(R.id.friday11_2);
        friday[11] = (AutoResizeTextView) getView().findViewById(R.id.friday11_3);
        friday[12] = (AutoResizeTextView) getView().findViewById(R.id.friday11_4);
        friday[13] = (AutoResizeTextView) getView().findViewById(R.id.friday12_1);
        friday[14] = (AutoResizeTextView) getView().findViewById(R.id.friday12_2);
        friday[15] = (AutoResizeTextView) getView().findViewById(R.id.friday12_3);
        friday[16] = (AutoResizeTextView) getView().findViewById(R.id.friday12_4);
        friday[17] = (AutoResizeTextView) getView().findViewById(R.id.friday13_1);
        friday[18] = (AutoResizeTextView) getView().findViewById(R.id.friday13_2);
        friday[19] = (AutoResizeTextView) getView().findViewById(R.id.friday13_3);
        friday[20] = (AutoResizeTextView) getView().findViewById(R.id.friday13_4);
        friday[21] = (AutoResizeTextView) getView().findViewById(R.id.friday14_1);
        friday[22] = (AutoResizeTextView) getView().findViewById(R.id.friday14_2);
        friday[23] = (AutoResizeTextView) getView().findViewById(R.id.friday14_3);
        friday[24] = (AutoResizeTextView) getView().findViewById(R.id.friday14_4);
        friday[25] = (AutoResizeTextView) getView().findViewById(R.id.friday15_1);
        friday[26] = (AutoResizeTextView) getView().findViewById(R.id.friday15_2);
        friday[27] = (AutoResizeTextView) getView().findViewById(R.id.friday15_3);
        friday[28] = (AutoResizeTextView) getView().findViewById(R.id.friday15_4);
        friday[29] = (AutoResizeTextView) getView().findViewById(R.id.friday16_1);
        friday[30] = (AutoResizeTextView) getView().findViewById(R.id.friday16_2);
        friday[31] = (AutoResizeTextView) getView().findViewById(R.id.friday16_3);
        friday[32] = (AutoResizeTextView) getView().findViewById(R.id.friday16_4);
        friday[33] = (AutoResizeTextView) getView().findViewById(R.id.friday17_1);
        friday[34] = (AutoResizeTextView) getView().findViewById(R.id.friday17_2);
        friday[35] = (AutoResizeTextView) getView().findViewById(R.id.friday17_3);
        friday[36] = (AutoResizeTextView) getView().findViewById(R.id.friday17_4);

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://coe516.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(HomeActivity.userID, "UTF-8");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //결과값 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼에담아두기
                String temp;    //하나씩 문자열로 읽으며 저장
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");  //한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect(); //연결해제
                return stringBuilder.toString().trim(); //문자열들 반환
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                ProgressDialog dialog = new ProgressDialog(getActivity());
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //각 공지사항리스트 저장
                dialog.setMessage("로딩중");   //DB연결중에 안정성위해 로딩중 표시
                dialog.show();
                int count = 0;
                String courseProfessor;
                String courseTime;
                String courseID;
                String courseTitle;
                while(count < jsonArray.length()) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getString("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");
                    schedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    count++;
                }
                dialog.dismiss();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            schedule.setting(monday, tuesday, wednesday, thursday, friday, getContext());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

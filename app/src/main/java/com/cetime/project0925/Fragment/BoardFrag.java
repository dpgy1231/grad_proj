package com.cetime.project0925.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.board.community.CommunityActivity;
import com.cetime.project0925.Fragment.board.notice.Notice;
import com.cetime.project0925.Fragment.board.notice.NoticeActivity;
import com.cetime.project0925.Fragment.board.notice.NoticeListAdapter;
import com.cetime.project0925.Fragment.board.notice.NoticeReadActivity;
import com.cetime.project0925.Fragment.board.proNotice.ProNoticeActivity;
import com.cetime.project0925.Fragment.board.toProfessor.FromStudentActivity;
import com.cetime.project0925.Fragment.board.toProfessor.ProfessorListActivity;
import com.cetime.project0925.Fragment.board.toProfessor.ToProfessorActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardFrag extends Fragment {
    //TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BoardFrag.OnFragmentInteractionListener mListener;

    public BoardFrag() {
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
    public static BoardFrag newInstance(String param1, String param2) {
        BoardFrag fragment = new BoardFrag();
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
        view = inflater.inflate(R.layout.frag_board, container, false);
        return view;
    }

    private View view;

    private ListView noticeListView;
    private NoticeListAdapter noticeListAdapter;
    private List<Notice> noticeList;

    private String userID = HomeActivity.userID;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        noticeListView = (ListView) getView().findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeListAdapter = new NoticeListAdapter(getContext().getApplicationContext(), noticeList, this);
        noticeListView.setAdapter(noticeListAdapter);

        final Button moreNoticeButton = (Button) view.findViewById(R.id.moreNotice);
        final Button moreProNoticeButton = (Button) view.findViewById(R.id.moreProNotice);
        final Button moreCommunityButton = (Button) view.findViewById(R.id.moreCommunity);
        final Button moreToProfessorButton = (Button) view.findViewById(R.id.moreToProfessor);

        moreNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        moreProNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProNoticeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        moreCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        moreToProfessorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomeActivity.userPriority == 2 || HomeActivity.userPriority==0) { //교수님이나 관리자라면
                    Intent intent = new Intent(getActivity(), FromStudentActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
                else{ //교수님이 아니라면
                    Intent intent = new Intent(getActivity(), ProfessorListActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }

            }
        });

        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(getActivity(), NoticeReadActivity.class);  //내용보기로 넘어가기
                //int index = noticeList.get(i).getIndex();
                //Notice noticeParam;
                //noticeParam = new Notice(noticeList.get(i).getIndex(), noticeList.get(i).getTag(), noticeList.get(i).getTitle(), noticeList.get(i).getContent(), noticeList.get(i).getWriter(), noticeList.get(i).getDate(), noticeList.get(i).getTime(), noticeList.get(i).getCount());

                intent.putExtra("index", noticeList.get(i).getIndex());
                intent.putExtra("tag", noticeList.get(i).getTag());
                intent.putExtra("title", noticeList.get(i).getTitle());
                intent.putExtra("content", noticeList.get(i).getContent());
                intent.putExtra("writer", noticeList.get(i).getWriter());
                intent.putExtra("date", noticeList.get(i).getDate());
                intent.putExtra("time", noticeList.get(i).getTime());
                intent.putExtra("count", noticeList.get(i).getCount()+1);
                intent.putExtra("noticeID", noticeList.get(i).getUserID());
                intent.putExtra("file", noticeList.get(i).getFile());
                intent.putExtra("backNum", 0);
                startActivity(intent);
                getActivity().finish();
            }
        });
        new BackgroundTask().execute();
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

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "https://coe516.cafe24.com/BoardList.php";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //결과값 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼에담아두기
                String temp;    //하나씩 문자열로 읽으며 저장
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");  //한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect(); //연결해제
                return stringBuilder.toString().trim(); //문자열들 반환
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try{
                JSONObject jsonObject = new JSONObject(result); //응답

                JSONArray jsonArray = jsonObject.getJSONArray("response"); //각 공지사항리스트 저장

                int count = 0;
                int end = jsonArray.length();
                int noticeIndex, noticeCount =0;
                String noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeUserID, noticeFile;
                if(jsonArray.length()>10)  end=10;
                while(count < end) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeIndex = object.getInt("noticeIndex");
                    noticeTag = object.getString("noticeTag");
                    noticeTitle = object.getString("noticeTitle");
                    noticeContent = object.getString("noticeContent");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    noticeTime = object.getString("noticeTime");
                    noticeCount = object.getInt("noticeCount");
                    noticeUserID = object.getString("noticeUserID");
                    noticeFile = object.getString("noticeFile");
                    Notice notice = new Notice(noticeIndex, noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeCount, noticeUserID, noticeFile);
                    noticeList.add(notice);         //noticelist에 공지하나
                    noticeListAdapter.notifyDataSetChanged();
                    count++;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


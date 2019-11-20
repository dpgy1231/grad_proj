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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cetime.project0925.Fragment.chat.Chat;
import com.cetime.project0925.Fragment.chat.ChatFindActivity;
import com.cetime.project0925.Fragment.chat.ChatRoom;
import com.cetime.project0925.Fragment.chat.ChatRoomActivity;
import com.cetime.project0925.Fragment.chat.ChatRoomListAdapter;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ChatFrag extends Fragment {
    //TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ChatFrag.OnFragmentInteractionListener mListener;

    public ChatFrag() {
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
    public static ChatFrag newInstance(String param1, String param2) {
        ChatFrag fragment = new ChatFrag();
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
        view = inflater.inflate(R.layout.frag_chat, container, false);

        return view;
    }

    private View view;

    private ListView chatRoomListView;
    private ChatRoomListAdapter chatRoomListAdapter;
    public List<ChatRoom> chatRoomList;
    public List<Chat> chatList;

    private String userID = HomeActivity.userID;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        Button sendButton = (Button) getView().findViewById(R.id.sendButton);

        chatRoomListView = (ListView) getView().findViewById(R.id.chatRoomListView);
        chatRoomList = new ArrayList<ChatRoom>();
        chatList = new ArrayList<Chat>();
        chatRoomListAdapter = new ChatRoomListAdapter(getContext().getApplicationContext(), chatRoomList, this);
        chatRoomListView.setAdapter(chatRoomListAdapter);

        chatRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {     //리스트 항목 클릭 이벤트
                Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                intent.putExtra("fragNum", 3);  //ChatFrag
                intent.putExtra("yourID", chatRoomList.get(i).getYourID());
                intent.putExtra("yourName", chatRoomList.get(i).getYourName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("chatList", (Serializable)chatList);
                intent.putExtra("BUNDLE",bundle);
                startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatFindActivity.class);  //내용보기로 넘어가기
                intent.putExtra("fragNum", 3);  //ChatFrag
                Bundle bundle = new Bundle();
                intent.putExtra("yourID", "");
                intent.putExtra("yourName", "");
                bundle.putSerializable("chatList", (Serializable)chatList);
                intent.putExtra("BUNDLE",bundle);
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
        protected void onPreExecute(){
            try{
                target = "http://coe516.cafe24.com/ChatList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            } catch (Exception e) {
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
                chatRoomList.clear();
                chatList.clear();
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int index=0;
                String yourID, yourName;
                int chatIndex, chatCount;
                String chatID1, chatName1, chatID2, chatName2, chatContent, chatDate, chatTime;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    chatIndex = object.getInt("chatIndex");
                    chatID1 = object.getString("chatID1");
                    chatName1 = object.getString("chatName1");
                    chatID2 = object.getString("chatID2");
                    chatName2 = object.getString("chatName2");
                    chatContent = object.getString("chatContent");
                    chatDate = object.getString("chatDate");
                    chatTime = object.getString("chatTime");
                    chatCount = object.getInt("chatCount");
                    Chat chat = new Chat(chatIndex, chatID1, chatName1, chatID2, chatName2, chatContent, chatDate, chatTime, chatCount);
                    chatList.add(chat);

                    if(chatID1.equals(userID)){ yourID = chatID2; yourName = chatName2; }
                    else{ yourID = chatID1; yourName = chatName1; }
                    Boolean check = false;
                    for(int i=0; i<chatRoomList.size(); i++){       //마지막 대화
                        if(chatRoomList.get(i).getYourID().equals(yourID)){
                            check=true;
                            chatRoomList.get(i).setContent(chatContent);
                            chatRoomList.get(i).setDate(chatDate);
                            chatRoomList.get(i).setTime(chatTime);
                        }
                    }
                    if(!check){
                        ChatRoom chatRoom = new ChatRoom(index++, yourID, yourName, chatContent, chatDate, chatTime);
                        chatRoomList.add(chatRoom);
                    }
                    count++;
                }
                chatRoomListAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

package com.cetime.project0925.Fragment.board.community;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.cetime.project0925.Fragment.board.community.CommunityActivity.community2List;

public class CommunityListAdapter extends BaseAdapter {
    private Context context;
    //private List<Community2> community2List;
    private List<Community> communityList;
    private String userID = HomeActivity.userID;
    public int communityIndex, commentIndex =0;
    public Community2 community2;

    public String heartUserID;
    public int heartCnt=0;
    //private int cmtCnt=0;
    private boolean heartValid = false;

    public CommunityListAdapter(Context context, List<Community> communityList) {
        this.context = context;
        this.communityList = communityList;
        try {
            new BackgroundTask().execute();
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return communityList.size();
    }

    @Override
    public Object getItem(int i) {
        return communityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_community, null);

            TextView communityTitle = (TextView) v.findViewById(R.id.communityTitle);
            TextView communityWriter = (TextView) v.findViewById(R.id.communityWriter);
            TextView communityDate = (TextView) v.findViewById(R.id.communityDate);
            TextView communityTime = (TextView) v.findViewById(R.id.communityTime);
            TextView communityHeartCount = (TextView) v.findViewById(R.id.communityHeartCount);
            TextView communityCount = (TextView) v.findViewById(R.id.communityCount);

            communityTitle.setText(communityList.get(i).getTitle());
            communityWriter.setText(communityList.get(i).getWriter());
            String communityDateString = communityList.get(i).getDate();
            String communityTimeString = communityList.get(i).getTime();
            communityDate.setText(communityDateString.substring(5, 7) + "/" + communityDateString.substring(8, 10));
            communityTime.setText(communityTimeString.substring(0, 2) + ":" + communityTimeString.substring(3, 5));
            communityCount.setText("조회수 " + communityList.get(i).getCount());

            heartCnt=0;
            //cmtCnt=0;
            heartValid=false;
            for(int cnt=0; cnt<community2List.size(); cnt++){
                if(community2List.get(cnt).getIndex()==communityList.get(i).getIndex()) {
                    heartCnt++;
                    if (userID.equals(community2List.get(cnt).getHeartUserID())) { heartValid = true; }
                    /*
                    if (community2List.get(cnt).getHeartUserID().equals("no")) { }
                    else {
                        heartCnt++;
                        if (userID.equals(community2List.get(cnt).getHeartUserID())) { heartValid = true; }
                    }

                   if(community2List.get(cnt).getCmIndex() == 0){}
                   else{ cmtCnt++;}

                     */
                }
            }


            if (heartValid) {
                communityHeartCount.setText("♥ " + heartCnt);
            } else {
                communityHeartCount.setText("♡ " + heartCnt);
            }
            //communityCommentCount.setText("댓글 " + cmtCnt);

            v.setTag(communityList.get(i).getTitle());

        return v;
    }
    public void remove(int position){
        communityList.remove(position);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://coe516.cafe24.com/CommunityList2.php";
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
                JSONObject jsonObject = new JSONObject(result); //응답
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //각 공지사항리스트 저장
                int count = 0;
                heartCnt =0;
                heartValid = false;
                while(count < jsonArray.length()) { //공지사항 수
                    JSONObject object = jsonArray.getJSONObject(count);
                    communityIndex = object.getInt("communityIndex");
                    heartUserID = object.getString("userID");
                    //if( object.optString("userID", "no").equals("null")){ heartUserID = "no"; }
                    //else{heartUserID = object.optString("userID","no");}
                    community2 = new Community2(communityIndex, heartUserID);
                    community2List.add(community2);         //communitylist에 글하나
                    count++;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

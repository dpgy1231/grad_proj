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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> commentList;
    private String userID = HomeActivity.userID;
    private int communityIndex;
    private String communityUserID;

    public CommentListAdapter(Context context, int communityIndex, String communityUserID, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        this.communityIndex = communityIndex;
        this.communityUserID = communityUserID;
        new BackgroundTask().execute();
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_comment, null);

        TextView commentContent = (TextView) v.findViewById(R.id.commentContent);
        TextView commentWriter = (TextView) v.findViewById(R.id.commentWriter);
        TextView commentDate = (TextView) v.findViewById(R.id.commentDate);
        TextView commentTime = (TextView) v.findViewById(R.id.commentTime);
        TextView commentMe = (TextView) v.findViewById(R.id.commentMe);

        commentContent.setText(commentList.get(i).getContent());
        if(communityUserID.equals(commentList.get(i).getUserID())){
            commentMe.setText("(글쓴이)");
        }
        else if(commentList.get(i).getUserID().equals(HomeActivity.userID)){
            commentMe.setText("(나)");
        }
        else{commentMe.setText("");}

        if(commentList.get(i).getTag().equals("익명")){
            commentWriter.setText("익명");
        }
        else commentWriter.setText(commentList.get(i).getWriter());
        String commentDateString = commentList.get(i).getDate();
        String commentTimeString = commentList.get(i).getTime();
        commentDate.setText(commentDateString.substring(5,7)+"/"+commentDateString.substring(8,10));
        commentTime.setText(commentTimeString.substring(0,2)+":"+commentTimeString.substring(3,5));
        //System.out.println();
        v.setTag(commentList.get(i).getIndex());
        return v;
    }
    public void remove(int position){
        commentList.remove(position);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                String communityIndexString = String.valueOf(communityIndex);
                target = "https://coe516.cafe24.com/CommentList.php?communityIndex=" + URLEncoder.encode(communityIndexString, "UTF-8");
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
                commentList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int commentIndex, coCommunityIndex;
                String commentTag, commentWriter, commentContent, commentDate, commentTime, commentUserID;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    commentIndex = object.getInt("cmIndex");
                    coCommunityIndex = object.getInt("communityIndex");
                    commentWriter = object.getString("cmuserName");
                    commentContent = object.getString("cmContent");
                    commentDate = object.getString("cmDate");
                    commentTime = object.getString("cmTime");
                    commentUserID = object.getString("cmuserID");
                    commentTag = object.getString("cmTag");
                    Comment comment = new Comment(commentIndex, coCommunityIndex, commentTag, commentWriter, commentContent, commentDate, commentTime, commentUserID);
                    commentList.add(comment);
                    count++;
                }
                //CommunityReadActivity.commentCnt = jsonArray.length();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

package com.cetime.project0925.Fragment.board.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.MEDIA_MOUNTED;

public class CommunityReadActivity extends AppCompatActivity {
    public String userID = HomeActivity.userID;
    public String userName = HomeActivity.userName;
    public int userPriority = HomeActivity.userPriority;

    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("hh:mm:ss");

    public String communityTag, communityTitle, communityContent, communityWriter, communityDate, communityTime, communityUserID, communityFile;
    public int communityCount,communityIndex;

    public String communityIndexString;
    private ListView commentListView;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentList;
    private AlertDialog dialog;

    String File_Name; //파일이름
    String File_extend; //확장자
    String fileURL = "http://coe516.cafe24.com/commaFile"; // URL
    String Save_Path;
    String Save_folder = "/COMMAdownload";
    ProgressBar loadingBar;
    DownloadThread dThread;

    public int heartCnt=0;
    private int cmtCnt=0;
    private boolean heartCheck = false;

    public List<Heart> heartList;

    private View.OnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_read);
        communityIndex = getIntent().getIntExtra("index", 1);
        communityTag = getIntent().getStringExtra("tag");
        communityTitle = getIntent().getStringExtra("title");
        communityContent = getIntent().getStringExtra("content");
        communityWriter = getIntent().getStringExtra("writer");
        communityDate = getIntent().getStringExtra("date");
        communityTime = getIntent().getStringExtra("time");
        communityCount = getIntent().getIntExtra("count",0);
        communityUserID = getIntent().getStringExtra("communityID");
        communityFile = getIntent().getStringExtra("file");
        heartCnt = getIntent().getIntExtra("heartCnt", 0);
        heartCheck = getIntent().getBooleanExtra("heartValid", false);
        //cmtCnt = getIntent().getIntExtra("cmtCnt", 0);


        communityIndexString = String.valueOf(communityIndex);
        heartList = new ArrayList<Heart>();

        TextView communityTitleText = (TextView) findViewById(R.id.communityTitle);
        TextView communityWriterText = (TextView) findViewById(R.id.communityWriter);
        TextView communityDateText = (TextView) findViewById(R.id.communityDate);
        TextView communityTimeText = (TextView) findViewById(R.id.communityTime);
        TextView communityCountText = (TextView) findViewById(R.id.communityCount);
        TextView communityContentText = (TextView) findViewById(R.id.communityContent);
        TextView communityFileText = (TextView) findViewById(R.id.communityFile);
        //TextView commentCountText = (TextView) findViewById(R.id.communityCommentCount);
        final CheckBox commentAnonymousCheck = (CheckBox) findViewById(R.id.commentAnonymousCheck);
        final EditText commentContent = (EditText) findViewById(R.id.commentContent);
        final Button communityHeartButton = (Button) findViewById(R.id.communityHeartButton);   //하트버튼

        communityTitleText.setText(communityTitle);
        communityContentText.setText(communityContent);
        communityWriterText.setText(communityWriter);
        communityDateText.setText(communityDate.substring(5,7)+"/"+communityDate.substring(8,10));
        communityTimeText.setText(communityTime.substring(0,2)+":"+communityTime.substring(3,5));
        communityCountText.setText("조회수 " + communityCount);
        if(heartCheck)
            communityHeartButton.setText("취소 ♥" + (heartCnt));
        else communityHeartButton.setText("♡" + (heartCnt));
        communityFileText.setText(communityFile);

        //System.out.println(cmtCnt + " readActivity");
        //commentCountText.setText("댓글 " + cmtCnt);

        commentListView = (ListView) findViewById(R.id.commentListView);
        commentList = new ArrayList<Comment>();
        commentListAdapter = new CommentListAdapter(this, communityIndex, communityUserID, commentList);
        commentListView.setAdapter(commentListAdapter);

        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int arg2, long arg3){  //댓글삭제
                final String commentIndexString = String.valueOf(commentList.get(arg2).getIndex());
                if(commentList.get(arg2).getUserID().equals(userID) || (userPriority==0)) {
                    new AlertDialog.Builder(CommunityReadActivity.this)
                            .setTitle("삭제 확인")
                            .setMessage("정말로 삭제하시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { dialog.cancel();}
                            })
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {  Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                commentList.remove(arg2);
                                                commentListAdapter.notifyDataSetChanged();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                                                AlertDialog dialog2 = builder.setMessage("삭제되었습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog2.show();
                                            }
                                        }catch (Exception e){  e.printStackTrace(); }
                                    }
                                };
                                    CommentDeleteRequest commentDeleteRequest = new CommentDeleteRequest(commentIndexString, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(CommunityReadActivity.this);
                                    queue.add(commentDeleteRequest);
                                }
                            }).setCancelable(false).create().show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                    dialog = builder.setMessage("접근 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                return true;
            }
        });

        Button backButton = (Button) findViewById(R.id.backButton);     //뒤로버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityReadActivity.this, CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button modifyButton = (Button) findViewById(R.id.modifyButton);     //수정버튼
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(communityUserID.equals(userID) || (userPriority==0)) {
                    Intent intent = new Intent(CommunityReadActivity.this, CommunityModifyActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    intent.putExtra("fragNum", 1);  //HomeActivity화면 boardFrag
                    intent.putExtra("index", communityIndex);
                    intent.putExtra("tag", communityTag);
                    intent.putExtra("title", communityTitle);
                    intent.putExtra("content", communityContent);
                    intent.putExtra("writer", communityWriter);
                    intent.putExtra("date", communityDate);
                    intent.putExtra("time", communityTime);
                    intent.putExtra("count", communityCount);
                    intent.putExtra("communityID", communityUserID);
                    startActivity(intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                    dialog = builder.setMessage("접근 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        if(communityFile.contains("첨부파일없음") || communityFile.equals("")){
        } else{
            File_Name =  communityFile;
            String[] array = communityFile.split("\\."); //db에서 받은 파일명 구분자로 파일이름과 확장자 분리
            File_extend = array[1];
            System.out.println(File_Name + "  " + File_extend);
        }

        Button btn = (Button) findViewById(R.id.downbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(communityFile.contains("첨부파일없음") || communityFile.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                    dialog = builder.setMessage("다운받을 첨부파일이 존재하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                    dialog = builder.setMessage("웹 페이지에서 첨부파일을 받아주세요ㅠ.ㅠ")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        if(communityFile.equals("첨부파일없음") || communityFile.equals("")){
        } else{
            loadingBar = (ProgressBar) findViewById(R.id.Loading);

            // 다운로드 경로를 외장메모리 사용자 지정 폴더로 함.
            String ext = Environment.getExternalStorageState();
            if (ext.equals(MEDIA_MOUNTED)) {
                Save_Path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + Save_folder;
            }
        }


        Button commentAddButton = (Button) findViewById(R.id.commentAddButton);     //댓글 등록
        commentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String commentContentText = commentContent.getText().toString();
                final String commentTag;
                if(commentAnonymousCheck.isChecked()){ commentTag = "익명"; }
                else{ commentTag = "공개"; }
                final String commentDateText = mFormat.format(System.currentTimeMillis());
                final String commentTimeText = mFormatTime.format(System.currentTimeMillis());

                if (commentContentText.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityReadActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    commentContent.setText("");
                                    commentAnonymousCheck.setChecked(false);
                                    commentListAdapter.notifyDataSetChanged();
                                    recreate();     //reload activity
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                } else {
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    CommentAddRequest commentAddRequest = new CommentAddRequest( communityIndexString, commentTag, userName, commentContentText, commentDateText, commentTimeText, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CommunityReadActivity.this);
                    queue.add(commentAddRequest);
                }
        });



        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(heartCheck){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    heartCnt -=1;
                                    if((heartCnt-1)<0)  heartCnt=0;
                                    communityHeartButton.setText("♡" + (heartCnt));
                                    heartCheck=false;
                                } else { }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    HeartDeleteRequest heartDeleteRequest = new HeartDeleteRequest( communityIndexString, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CommunityReadActivity.this);
                    queue.add(heartDeleteRequest);
                }
                if(!heartCheck){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    heartCnt+=1;
                                    communityHeartButton.setText("취소 ♥" + (heartCnt));
                                    heartCheck=true;
                                } else { }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    HeartAddRequest heartAddRequest = new HeartAddRequest( communityIndexString, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CommunityReadActivity.this);
                    queue.add(heartAddRequest);
                }
            }
        };
        communityHeartButton.setOnClickListener(listener);

        Response.Listener<String> responseListener = new Response.Listener<String>() {  //조회수
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){ }
                }catch (Exception e){ e.printStackTrace(); }
            }
        };
        String communityIndexString = String.valueOf(communityIndex);
        String communityCountString = String.valueOf(communityCount);
        CommunityCountRequest communityCountRequest = new CommunityCountRequest(communityIndexString, communityCountString, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CommunityReadActivity.this);
        queue.add(communityCountRequest);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(CommunityReadActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

    class DownloadThread extends Thread {
        String ServerUrl;
        String LocalPath;

        DownloadThread(String serverPath, String localPath) {
            ServerUrl = serverPath;
            LocalPath = localPath;
        }

        @Override
        public void run() {
            URL imgurl;
            int Read;
            try {
                imgurl = new URL(ServerUrl);
                HttpURLConnection conn = (HttpURLConnection) imgurl
                        .openConnection();
                int len = conn.getContentLength();
                byte[] tmpByte = new byte[len];
                InputStream is = conn.getInputStream();
                File file = new File(LocalPath);
                FileOutputStream fos = new FileOutputStream(file);
                for (;;) {
                    Read = is.read(tmpByte);
                    if (Read <= 0) {
                        break;
                    }
                    fos.write(tmpByte, 0, Read);
                }
                is.close();
                fos.close();
                conn.disconnect();

            } catch (MalformedURLException e) {
                Log.e("ERROR1", e.getMessage());
            } catch (IOException e) {
                Log.e("ERROR2", e.getMessage());
                e.printStackTrace();
            }
            mAfterDown.sendEmptyMessage(0);
        }
    }

    Handler mAfterDown = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            loadingBar.setVisibility(View.GONE);
            // 파일 다운로드 종료 후 다운받은 파일을 실행시킨다.
            showDownloadFile();
        }

    };

    private void showDownloadFile() {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(Save_Path + "/" + File_Name);

        // 파일 확장자 별로 mime type 지정해 준다.
        if (File_extend.equals("mp3")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "audio/*");
        } else if (File_extend.equals("mp4")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "vidio/*");
        } else if (File_extend.equals("jpg") || File_extend.equals("jpeg")
                || File_extend.equals("JPG") || File_extend.equals("gif")
                || File_extend.equals("png") || File_extend.equals("bmp")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "image/*");
        } else if (File_extend.equals("txt")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "text/*");
        } else if (File_extend.equals("doc") || File_extend.equals("docx")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "application/msword");
        } else if (File_extend.equals("xls") || File_extend.equals("xlsx")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file),
                    "application/vnd.ms-excel");
        } else if (File_extend.equals("ppt") || File_extend.equals("pptx")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file),
                    "application/vnd.ms-powerpoint");
        } else if (File_extend.equals("pdf")) {
            intent.setDataAndType(FileProvider.getUriForFile(CommunityReadActivity.this, "com.cetime.project0925.fileprovider", file), "application/pdf");
        }
        startActivity(intent);
    }
}

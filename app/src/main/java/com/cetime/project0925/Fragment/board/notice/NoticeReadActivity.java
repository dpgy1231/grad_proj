package com.cetime.project0925.Fragment.board.notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.os.Environment.MEDIA_MOUNTED;
import static java.security.AccessController.getContext;

public class NoticeReadActivity extends AppCompatActivity{

    public Notice notice;
    public int noticeIndex;
    public String userID = HomeActivity.userID;
    public int userPriority = HomeActivity.userPriority;
    public String noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeUserID, noticeFile;
    public int noticeCount;
    private AlertDialog dialog;
    Context context;
    String File_Name; //파일이름
    String File_extend; //확장자

    String fileURL = "http://coe516.cafe24.com/commaFile"; // URL
    String Save_Path;
    String Save_folder = "/COMMAdownload";

    ProgressBar loadingBar;
    DownloadThread dThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_read);

        //Intent intent = getIntent();
       // noticeIndex = getIntent().getIntExtra("noticeIndex", 1);
       // notice = (Notice) getIntent().getSerializableExtra("noticeParam");
        //System.out.println(noticeIndex);
        context=NoticeReadActivity.this;
        noticeIndex = getIntent().getIntExtra("index", 1);
        noticeTag = getIntent().getStringExtra("tag");
        noticeTitle = getIntent().getStringExtra("title");
        noticeContent = getIntent().getStringExtra("content");
        noticeWriter = getIntent().getStringExtra("writer");
        noticeDate = getIntent().getStringExtra("date");
        noticeTime = getIntent().getStringExtra("time");
        noticeCount = getIntent().getIntExtra("count",0);
        noticeUserID = getIntent().getStringExtra("noticeID");
        noticeFile = getIntent().getStringExtra("file");

        final int backNum = getIntent().getIntExtra("backNum",0);

        TextView noticeTagText = (TextView) findViewById(R.id.noticeTag);
        TextView noticeTitleText = (TextView) findViewById(R.id.noticeTitle);
        TextView noticeWriterText = (TextView) findViewById(R.id.noticeWriter);
        TextView noticeDateText = (TextView) findViewById(R.id.noticeDate);
        TextView noticeCountText = (TextView) findViewById(R.id.noticeCount);
        TextView noticeContentText = (TextView) findViewById(R.id.noticeContent);
        TextView noticeFileText = (TextView) findViewById(R.id.noticeFile);

        if(noticeFile.equals("첨부파일없음") || noticeFile.equals("")){
        } else{
            File_Name =  noticeFile;
            String[] array = noticeFile.split("\\."); //db에서 받은 파일명 구분자로 파일이름과 확장자 분리
            File_extend = array[1];
            System.out.println(File_Name + "  " + File_extend);
        }

        Button btn = (Button) findViewById(R.id.downbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noticeFile.equals("첨부파일없음") || noticeFile.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeReadActivity.this);
                    dialog = builder.setMessage("다운받을 첨부파일이 존재하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeReadActivity.this);
                    dialog = builder.setMessage("웹 페이지에서 첨부파일을 받아주세요ㅠ.ㅠ")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
/*
                    if (view.getId() == R.id.downbtn) {
                        File dir = new File(Save_Path);
                        // 폴더가 존재하지 않을 경우 폴더를 만듦
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) // mounted
                                && context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) { // have permissions

                            File myTempDir = new File(getExternalCacheDir(), "tempDir");
                            myTempDir.mkdirs();

                            // create and write to files
                        }
                        // 다운로드 폴더에 동일한 파일명이 존재하는지 확인해서
                        // 없으면 다운받고 있으면 해당 파일 실행시킴.
                        if (new File(Save_Path + "/" + File_Name).exists() == false) {
                            loadingBar.setVisibility(View.VISIBLE);
                            dThread = new DownloadThread(fileURL + "/" + File_Name,
                                    Save_Path + "/" + File_Name);
                            dThread.start();
                        } else {
                            showDownloadFile();
                        }
                    }
*/
                }
            }
        });

        if(noticeFile.equals("첨부파일없음") || noticeFile.equals("")){
        } else{
            loadingBar = (ProgressBar) findViewById(R.id.Loading);

            // 다운로드 경로를 외장메모리 사용자 지정 폴더로 함.
            String ext = Environment.getExternalStorageState();
            if (ext.equals(MEDIA_MOUNTED)) {
                Save_Path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + Save_folder;
            }
        }


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backNum==1) {
                    Intent intent = new Intent(NoticeReadActivity.this, NoticeActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(NoticeReadActivity.this, HomeActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    intent.putExtra("fragNum", 1);  //HomeActivity화면
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noticeUserID.equals(userID)||(userPriority==0)) {
                    Intent intent = new Intent(NoticeReadActivity.this, NoticeModifyActivity.class);
                    intent.putExtra("userID", HomeActivity.userID);
                    intent.putExtra("fragNum", 1);  //HomeActivity화면
                    intent.putExtra("backNum", backNum);
                    intent.putExtra("index", noticeIndex);
                    intent.putExtra("tag", noticeTag);
                    intent.putExtra("title", noticeTitle);
                    intent.putExtra("content", noticeContent);
                    intent.putExtra("writer", noticeWriter);
                    intent.putExtra("date", noticeDate);
                    intent.putExtra("time", noticeTime);
                    intent.putExtra("count", noticeCount);
                    intent.putExtra("noticeID", noticeUserID);
                    intent.putExtra("file", noticeFile);
                    startActivity(intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeReadActivity.this);
                    dialog = builder.setMessage("접근 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        noticeTagText.setText('[' + noticeTag + ']');
        noticeTitleText.setText(" "+ noticeTitle);
        noticeContentText.setText(noticeContent);
        noticeWriterText.setText(noticeWriter);
        noticeDateText.setText(noticeDate);
        noticeCountText.setText("조회수 " + noticeCount);
        noticeFileText.setText(noticeFile);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        String noticeIndexString = String.valueOf(noticeIndex);
        String noticeCountString = String.valueOf(noticeCount);
        NoticeCountRequest noticeCountRequest = new NoticeCountRequest(noticeIndexString, noticeCountString, responseListener);
        RequestQueue queue = Volley.newRequestQueue(NoticeReadActivity.this);
        queue.add(noticeCountRequest);
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
        Toast.makeText(NoticeReadActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
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
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "audio/*");
            } else if (File_extend.equals("mp4")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "vidio/*");
            } else if (File_extend.equals("jpg") || File_extend.equals("jpeg")
                    || File_extend.equals("JPG") || File_extend.equals("gif")
                    || File_extend.equals("png") || File_extend.equals("bmp")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "image/*");
            } else if (File_extend.equals("txt")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "text/*");
            } else if (File_extend.equals("doc") || File_extend.equals("docx")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "application/msword");
            } else if (File_extend.equals("xls") || File_extend.equals("xlsx")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file),
                        "application/vnd.ms-excel");
            } else if (File_extend.equals("ppt") || File_extend.equals("pptx")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file),
                        "application/vnd.ms-powerpoint");
            } else if (File_extend.equals("pdf")) {
                intent.setDataAndType(FileProvider.getUriForFile(NoticeReadActivity.this, "com.cetime.project0925.fileprovider", file), "application/pdf");
            }
            startActivity(intent);
        }
}

package com.cetime.project0925.Fragment.board.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class NoticeModifyActivity extends AppCompatActivity {

    public Notice notice;
    public int noticeIndex;
    public String userID = HomeActivity.userID;
    public String noticeTag, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTime, noticeUserID;
    public int noticeCount;
    private ArrayAdapter noticeTagAdapter;
    private Spinner noticeTagSpinner;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_modify);

        noticeIndex = getIntent().getIntExtra("index", 1);
        noticeTag = getIntent().getStringExtra("tag");
        noticeTitle = getIntent().getStringExtra("title");
        noticeContent = getIntent().getStringExtra("content");
        noticeWriter = getIntent().getStringExtra("writer");
        noticeDate = getIntent().getStringExtra("date");
        noticeTime = getIntent().getStringExtra("time");
        noticeCount = getIntent().getIntExtra("count",0);
        noticeUserID = getIntent().getStringExtra("noticeID");
        final int backNum = getIntent().getIntExtra("backNum",0);

        noticeTagSpinner = (Spinner) findViewById(R.id.noticeTagSpinner);
        noticeTagAdapter = ArrayAdapter.createFromResource(this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        noticeTagSpinner.setAdapter(noticeTagAdapter);
        switch(noticeTag){
            case "전체":
                noticeTagSpinner.setSelection(0);
                break;
            case "1학년":
                noticeTagSpinner.setSelection(1);
                break;
            case "2학년":
                noticeTagSpinner.setSelection(2);
                break;
            case "3학년":
                noticeTagSpinner.setSelection(3);
                break;
            case "4학년":
                noticeTagSpinner.setSelection(4);
                break;
        }

        final EditText noticeTitleText = (EditText) findViewById(R.id.noticeTitle);
        noticeTitleText.setText(noticeTitle);
        final EditText noticeContentText= (EditText) findViewById(R.id.noticeContent);
        noticeContentText.setText(noticeContent);


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NoticeModifyActivity.this);
                dialog  .setTitle("취소 확인")
                        .setMessage("정말 작성을 중단하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("중단합니다.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(backNum==1) {
                                    Intent intent = new Intent( NoticeModifyActivity.this, NoticeActivity.class);
                                    intent.putExtra("userID", HomeActivity.userID);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Intent intent = new Intent(NoticeModifyActivity.this, HomeActivity.class);
                                    intent.putExtra("userID", HomeActivity.userID);
                                    intent.putExtra("fragNum", 1);  //HomeActivity화면
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).create().show();
            }
        });


        Button modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noticeTitle = noticeTitleText.getText().toString();
                String noticeContent = noticeContentText.getText().toString();
                String noticeTag = noticeTagSpinner.getSelectedItem().toString();
                String noticeDate = mFormat.format(System.currentTimeMillis());
                String noticeTime = mFormatTime.format(System.currentTimeMillis());

                if(noticeTitle.equals("") || noticeContent.equals("") || noticeTag.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeModifyActivity.this);
                    dialog = builder.setMessage("빈 칸을 모두 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder dialog = new AlertDialog.Builder(NoticeModifyActivity.this);
                                dialog  .setTitle("확인")
                                        .setMessage("작성을 완료하시겠습니까?")
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(backNum==1) {
                                                    Intent intent = new Intent( NoticeModifyActivity.this, NoticeActivity.class);
                                                    intent.putExtra("userID", HomeActivity.userID);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    Intent intent = new Intent(NoticeModifyActivity.this, HomeActivity.class);
                                                    intent.putExtra("userID", HomeActivity.userID);
                                                    intent.putExtra("fragNum", 1);  //HomeActivity화면
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }).create().show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeModifyActivity.this);
                                dialog = builder.setMessage("게시물 수정에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                String noticeIndexString = String.valueOf(noticeIndex);
                NoticeModifyRequest noticeModifyRequest = new NoticeModifyRequest(noticeIndexString, noticeTag, noticeTitle, noticeContent, noticeDate, noticeTime, noticeUserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(NoticeModifyActivity.this);
                queue.add(noticeModifyRequest);
            }
        });
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
        Toast.makeText(NoticeModifyActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

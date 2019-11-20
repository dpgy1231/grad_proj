package com.cetime.project0925.Fragment.board.proNotice;

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

public class ProNoticeModifyActivity extends AppCompatActivity {

    public int proIndex;
    public String userID = HomeActivity.userID;
    public String proTag, proTitle, proContent, proWriter, proDate, proTime, proUserID;
    public int proCount;
    private ArrayAdapter proTagAdapter;
    private Spinner proTagSpinner;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_notice_modify);

        proIndex = getIntent().getIntExtra("index", 1);
        proTag = getIntent().getStringExtra("tag");
        proTitle = getIntent().getStringExtra("title");
        proContent = getIntent().getStringExtra("content");
        proWriter = getIntent().getStringExtra("writer");
        proDate = getIntent().getStringExtra("date");
        proTime = getIntent().getStringExtra("time");
        proCount = getIntent().getIntExtra("count",0);
        proUserID = getIntent().getStringExtra("proUserID");

        proTagSpinner = (Spinner) findViewById(R.id.proTagSpinner);
        proTagAdapter = ArrayAdapter.createFromResource(this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        proTagSpinner.setAdapter(proTagAdapter);
        switch(proTag){
            case "전체":
                proTagSpinner.setSelection(0);
                break;
            case "1학년":
                proTagSpinner.setSelection(1);
                break;
            case "2학년":
                proTagSpinner.setSelection(2);
                break;
            case "3학년":
                proTagSpinner.setSelection(3);
                break;
            case "4학년":
                proTagSpinner.setSelection(4);
                break;
        }

        final EditText proTitleText = (EditText) findViewById(R.id.proTitle);
        proTitleText.setText(proTitle);
        final EditText proContentText= (EditText) findViewById(R.id.proContent);
        proContentText.setText(proContent);


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProNoticeModifyActivity.this);
                dialog  .setTitle("취소 확인")
                        .setMessage("정말 작성을 중단하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        })
                        .setPositiveButton("중단합니다.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent( ProNoticeModifyActivity.this, ProNoticeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).create().show();
            }
        });


        Button modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proTitle = proTitleText.getText().toString();
                String proContent = proContentText.getText().toString();
                String proTag = proTagSpinner.getSelectedItem().toString();
                String proDate = mFormat.format(System.currentTimeMillis());
                String proTime = mFormatTime.format(System.currentTimeMillis());

                if(proTitle.equals("") || proContent.equals("") || proTag.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeModifyActivity.this);
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
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ProNoticeModifyActivity.this);
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
                                                Intent intent = new Intent( ProNoticeModifyActivity.this, ProNoticeActivity.class);
                                                intent.putExtra("userID", HomeActivity.userID);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).create().show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeModifyActivity.this);
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
                String proIndexString = String.valueOf(proIndex);
                ProNoticeModifyRequest proNoticeModifyRequest = new ProNoticeModifyRequest(proIndexString, proTag, proTitle, proContent, proDate, proTime, proUserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProNoticeModifyActivity.this);
                queue.add(proNoticeModifyRequest);
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
        Toast.makeText(ProNoticeModifyActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

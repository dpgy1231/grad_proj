package com.cetime.project0925.Fragment.board.community;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class CommunityModifyActivity extends AppCompatActivity {
    public Community community;
    public int communityIndex;
    public String userID = HomeActivity.userID;
    public String communityTag, communityTitle, communityContent, communityWriter, communityDate, communityTime, communityUserID;
    public int communityCount, communityHeart;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");
    private AlertDialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_modify);

        communityIndex = getIntent().getIntExtra("index", 1);
        communityTag = getIntent().getStringExtra("tag");
        communityTitle = getIntent().getStringExtra("title");
        communityContent = getIntent().getStringExtra("content");
        communityWriter = getIntent().getStringExtra("writer");
        communityDate = getIntent().getStringExtra("date");
        communityTime = getIntent().getStringExtra("time");
        communityCount = getIntent().getIntExtra("count",0);
        communityHeart = getIntent().getIntExtra("heart",0);
        communityUserID = getIntent().getStringExtra("communityID");

        final EditText communityTitleText = (EditText) findViewById(R.id.communityTitle);
        communityTitleText.setText(communityTitle);
        final EditText communityContentText= (EditText) findViewById(R.id.communityContent);
        communityContentText.setText(communityContent);
        final CheckBox communityAnonymousCheck = (CheckBox) findViewById(R.id.communityAnonymousCheck);
        if(communityTag.equals("익명")){communityAnonymousCheck.setChecked(true);}
        else{communityAnonymousCheck.setChecked(false);}

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CommunityModifyActivity.this);
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
                                Intent intent = new Intent( CommunityModifyActivity.this, CommunityActivity.class);
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
                String communityTitle = communityTitleText.getText().toString();
                String communityContent = communityContentText.getText().toString();
                String communityTag;
                String communityDate = mFormat.format(System.currentTimeMillis());
                String communityTime = mFormatTime.format(System.currentTimeMillis());
                if(communityAnonymousCheck.isChecked()){
                    communityTag = communityAnonymousCheck.getText().toString();
                }
                else{ communityTag = "공개"; }
                if(communityTitle.equals("") || communityContent.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityModifyActivity.this);
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
                                AlertDialog.Builder dialog = new AlertDialog.Builder(CommunityModifyActivity.this);
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
                                                Intent intent = new Intent( CommunityModifyActivity.this, CommunityActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).create().show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CommunityModifyActivity.this);
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
                String communityIndexString = String.valueOf(communityIndex);
                CommunityModifyRequest communityModifyRequest = new CommunityModifyRequest(communityIndexString, communityTag, communityTitle, communityContent, communityDate, communityTime, communityUserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CommunityModifyActivity.this);
                queue.add(communityModifyRequest);
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
        Toast.makeText(CommunityModifyActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

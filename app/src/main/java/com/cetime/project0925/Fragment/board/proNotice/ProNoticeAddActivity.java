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

public class ProNoticeAddActivity extends AppCompatActivity {
    private ArrayAdapter proTagAdapter;
    private Spinner proTagSpinner;
    private String userID = HomeActivity.userID;
    private int userPriority = HomeActivity.userPriority;
    private String userName = HomeActivity.userName;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_notice_add);

        proTagSpinner = (Spinner) findViewById(R.id.proTagSpinner);
        proTagAdapter = ArrayAdapter.createFromResource(this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        proTagSpinner.setAdapter(proTagAdapter);
        proTagSpinner.setSelection(0);

        final EditText proTitleText = (EditText) findViewById(R.id.proTitle);
        final EditText proContentText= (EditText) findViewById(R.id.proContent);

        final Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProNoticeAddActivity.this);
                dialog  .setTitle("취소 확인")
                        .setMessage("정말 작성을 중단하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        })
                        .setPositiveButton("중단합니다.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ProNoticeAddActivity.this, ProNoticeActivity.class);
                                intent.putExtra("userID", HomeActivity.userID);
                                ProNoticeAddActivity.this.startActivity(intent);
                                finish();
                            }
                        }).create().show();
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPriority == 0 || userPriority == 2) { //0:관리자 2:교수님
                    String proTitle = proTitleText.getText().toString();
                    String proContent = proContentText.getText().toString();
                    String proTag = proTagSpinner.getSelectedItem().toString();
                    String proDate = mFormat.format(System.currentTimeMillis());
                    String proTime = mFormatTime.format(System.currentTimeMillis());

                    if (proTitle.equals("") || proContent.equals("") || proTag.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeAddActivity.this);
                        dialog = builder.setMessage("빈 칸을 모두 채워주세요.")
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeAddActivity.this);
                                    dialog = builder.setMessage("게시물이 성공적으로 등록되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Intent intent = new Intent(ProNoticeAddActivity.this, ProNoticeActivity.class);
                                    intent.putExtra("userID", HomeActivity.userID);
                                    ProNoticeAddActivity.this.startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeAddActivity.this);
                                    dialog = builder.setMessage("게시물 등록에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ProNoticeAddRequest proNoticeAddRequest = new ProNoticeAddRequest(proTag, proTitle, proContent, userName, proDate, proTime, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProNoticeAddActivity.this);
                    queue.add(proNoticeAddRequest);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProNoticeAddActivity.this);
                    dialog = builder.setMessage("접근 권한이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
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
        Toast.makeText(ProNoticeAddActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

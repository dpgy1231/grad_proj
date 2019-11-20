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

public class NoticeAddActivity extends AppCompatActivity {

    private ArrayAdapter noticeTagAdapter;
    private Spinner noticeTagSpinner;
    private String userID = HomeActivity.userID;
    private int userPriority = HomeActivity.userPriority;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_add);

        noticeTagSpinner = (Spinner) findViewById(R.id.noticeTagSpinner);
        noticeTagAdapter = ArrayAdapter.createFromResource(this, R.array.noticeTag, android.R.layout.simple_spinner_dropdown_item);
        noticeTagSpinner.setAdapter(noticeTagAdapter);
        noticeTagSpinner.setSelection(0);

        final EditText noticeTitleText = (EditText) findViewById(R.id.noticeTitle);
        final EditText noticeContentText= (EditText) findViewById(R.id.noticeContent);

        final Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NoticeAddActivity.this);
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
                                Intent intent = new Intent(NoticeAddActivity.this, NoticeActivity.class);
                                intent.putExtra("userID", HomeActivity.userID);
                                NoticeAddActivity.this.startActivity(intent);
                                finish();
                            }
                        }).create().show();
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPriority==0)   { //0:관리자
                    String noticeTitle = noticeTitleText.getText().toString();
                    String noticeContent = noticeContentText.getText().toString();
                    String noticeTag = noticeTagSpinner.getSelectedItem().toString();
                    String noticeDate = mFormat.format(System.currentTimeMillis());
                    String noticeTime = mFormatTime.format(System.currentTimeMillis());

                    if(noticeTitle.equals("") || noticeContent.equals("") || noticeTag.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeAddActivity.this);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeAddActivity.this);
                                    dialog = builder.setMessage("게시물이 성공적으로 등록되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Intent intent = new Intent(NoticeAddActivity.this, NoticeActivity.class);
                                    intent.putExtra("userID", HomeActivity.userID);
                                    NoticeAddActivity.this.startActivity(intent);
                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeAddActivity.this);
                                    dialog = builder.setMessage("게시물 등록에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    NoticeAddRequest noticeAddRequest = new NoticeAddRequest(noticeTag, noticeTitle, noticeContent, noticeDate, noticeTime, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(NoticeAddActivity.this);
                    queue.add(noticeAddRequest);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeAddActivity.this);
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
        Toast.makeText(NoticeAddActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

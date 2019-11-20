package com.cetime.project0925.Fragment.board.toProfessor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ToProfessorActivity extends AppCompatActivity {

    private ArrayAdapter toProTagAdapter;
    private Spinner toProTagSpinner;
    private String userID = HomeActivity.userID;
    private int userPriority = HomeActivity.userPriority;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd");
    SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm:ss");

    private String toProName;

    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_professor);

        toProName = getIntent().getStringExtra("toProName");

        TextView toProNameText = (TextView) findViewById(R.id.toProNameText);
        final EditText toProTitleText = (EditText) findViewById(R.id.toProTitle);
        final EditText toProContentText = (EditText) findViewById(R.id.toProContent);
        final CheckBox toProAnonymousCheck = (CheckBox) findViewById(R.id.toProAnonymousCheck);

        toProNameText.setText(toProName + " 교수님께 글 쓰기");
        final Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ToProfessorActivity.this);
                dialog.setTitle("취소 확인")
                        .setMessage("이대로 작성을 중단하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("중단합니다.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ToProfessorActivity.this, HomeActivity.class);
                                intent.putExtra("userID", HomeActivity.userID);
                                intent.putExtra("fragNum", 1);  //HomeActivity화면
                                ToProfessorActivity.this.startActivity(intent);
                                finish();
                            }
                        }).setCancelable(false).create().show();
            }
        });
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toProTitle = toProTitleText.getText().toString();
                String toProContent = toProContentText.getText().toString();
                String toProDate = mFormat.format(System.currentTimeMillis());
                String toProTime = mFormatTime.format(System.currentTimeMillis());
                String toProTag;
                if(toProAnonymousCheck.isChecked()){
                    toProTag = toProAnonymousCheck.getText().toString();
                }
                else{ toProTag = "공개"; }

                if (toProTitle.equals("") || toProContent.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ToProfessorActivity.this);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(ToProfessorActivity.this);
                                dialog = builder.setMessage("글이 성공적으로 등록되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(ToProfessorActivity.this, HomeActivity.class);
                                intent.putExtra("userID", HomeActivity.userID);
                                intent.putExtra("fragNum", 1);  //HomeActivity화면
                                ToProfessorActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ToProfessorActivity.this);
                                dialog = builder.setMessage("등록에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ToProfessorRequest toProfessorRequest = new ToProfessorRequest(toProTag, toProName, toProTitle, toProContent, HomeActivity.userName, toProDate, toProTime, HomeActivity.userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ToProfessorActivity.this);
                queue.add(toProfessorRequest);
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
        Toast.makeText(ToProfessorActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

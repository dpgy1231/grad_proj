package com.cetime.project0925.Fragment.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

public class MypageModifyActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private String userID;
    private String userPassword;
    private String userPasswordCheck;
    private String userName;
    private String userEmail;
    private String userGender;
    private String userGrade;
    private String userPhone;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_modify);

        final TextView idText = (TextView) findViewById(R.id.idText);
        idText.setText("아이디(학번) : " + HomeActivity.userID);

        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final EditText pwCheckText = (EditText) findViewById(R.id.pwCheckText);
        final EditText gradeText = (EditText) findViewById(R.id.gradeText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText phoneText = (EditText) findViewById(R.id.phoneText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderRadio);

        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });


        Button modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = HomeActivity.userID;
                String userPassword = pwText.getText().toString();
                String userPasswordCheck = pwCheckText.getText().toString();
                final String userName = nameText.getText().toString();
                final String userGrade = gradeText.getText().toString();
                final String userEmail = emailText.getText().toString();
                final String userPhone = phoneText.getText().toString();

                if (userPassword.equals("") || userPasswordCheck.equals("") || userName.equals("") || userGender.equals("") || userGrade.equals("") || userEmail.equals("") || userPhone.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageModifyActivity.this);
                    dialog = builder.setMessage("빈 칸을 모두 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (!userPassword.equals(userPasswordCheck)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageModifyActivity.this);
                    dialog = builder.setMessage("비밀번호와 비밀번호 확인이 동일하지 않습니다. 다시 입력해주세요")
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
                                new AlertDialog.Builder(MypageModifyActivity.this).setTitle("확인")
                                        .setMessage("회원정보를 성공적으로 변경하였습니다.")
                                        .setNegativeButton("취소", null)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                nameText.setText("");
                                                pwCheckText.setText("");
                                                pwText.setText("");
                                                emailText.setText("");
                                                gradeText.setText("");
                                                phoneText.setText("");
                                                Intent intent = new Intent(MypageModifyActivity.this, MypageCheckActivity.class);
                                                intent.putExtra("userName", userName);
                                                intent.putExtra("userGender", userGender);
                                                intent.putExtra("userGrade", userGrade);
                                                intent.putExtra("userEmail", userEmail);
                                                intent.putExtra("userPhone", userPhone);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).setCancelable(false).create().show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageModifyActivity.this);
                                dialog = builder.setMessage("회원정보 수정에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                MypageRequest mypageRequest = new MypageRequest(userID, userPassword, userName, userGender, userGrade, userEmail, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MypageModifyActivity.this);
                queue.add(mypageRequest);

            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageModifyActivity.this, MypageCheckActivity.class);
                intent.putExtra("userName", HomeActivity.userName);
                intent.putExtra("userGender", HomeActivity.userGender);
                intent.putExtra("userGrade", HomeActivity.userGrade);
                intent.putExtra("userEmail", HomeActivity.userEmail);
                intent.putExtra("userPhone", HomeActivity.userPhone);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onStop(){
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
        Toast.makeText(MypageModifyActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}

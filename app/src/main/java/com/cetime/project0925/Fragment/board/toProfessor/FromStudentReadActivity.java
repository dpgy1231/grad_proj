package com.cetime.project0925.Fragment.board.toProfessor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.Fragment.HomeActivity;
import com.cetime.project0925.R;

import org.json.JSONObject;

public class FromStudentReadActivity extends AppCompatActivity {

    private int fromStuIndex=1;
    private String fromStuTag, fromStuName, fromStuTitle, fromStuContent, fromStuWriter, fromStuDate, fromStuTime, fromStuUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_student_read);

        fromStuIndex = getIntent().getIntExtra("index", 1);
        fromStuTag = getIntent().getStringExtra("tag");
        fromStuName = getIntent().getStringExtra("name");
        fromStuTitle = getIntent().getStringExtra("title");
        fromStuContent = getIntent().getStringExtra("content");
        fromStuWriter = getIntent().getStringExtra("writer");
        fromStuDate = getIntent().getStringExtra("date");
        fromStuTime = getIntent().getStringExtra("time");
        fromStuUserID = getIntent().getStringExtra("studentID");

        TextView professorText = (TextView) findViewById(R.id.professor);
        TextView fromStuTitleText = (TextView) findViewById(R.id.fromStuTitle);
        TextView fromStuWriterText = (TextView) findViewById(R.id.fromStuWriter);
        TextView fromStuDateText = (TextView) findViewById(R.id.fromStuDate);
        TextView fromStuTimeText = (TextView) findViewById(R.id.fromStuTime);
        TextView fromStuContentText = (TextView) findViewById(R.id.fromStuContent);

        professorText.setText("["+fromStuName+" 교수님께]");
        fromStuTitleText.setText(" "+ fromStuTitle);
        fromStuContentText.setText(fromStuContent);
        fromStuWriterText.setText(fromStuWriter);
        fromStuDateText.setText(fromStuDate.substring(5, 7) + "/" + fromStuDate.substring(8, 10));
        fromStuTimeText.setText(fromStuTime.substring(0, 2) + ":" + fromStuTime.substring(3, 5));

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FromStudentReadActivity.this, FromStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) { // 1.5초이내에 뒤로 한번더 누르면 종료
            finish();
            return;
        }
        Toast.makeText(FromStudentReadActivity.this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
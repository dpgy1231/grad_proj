package com.cetime.project0925.Fragment.schedule;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cetime.project0925.*;
import com.cetime.project0925.Fragment.HomeActivity;

import org.json.JSONObject;

import java.util.List;

public class DeleteCourseAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private String userID = HomeActivity.userID;

    public DeleteCourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;

    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.delete_course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        final TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        String grade = courseList.get(i).getCourseGrade();

        if(grade.contains("제한없음") || grade.equals("") || TextUtils.isEmpty(grade) || (grade==null)){  //null값 못 골라냄,,,
            courseGrade.setText("전학년");
        }
        else{
            courseGrade.setText(grade + "학년" );
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseDivide.setText("분반0" + courseList.get(i).getCourseDivide());
        courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        courseTime.setText(courseList.get(i).getCourseTime() + "");
        courseRoom.setText(courseList.get(i).getCourseRoom());

        v.setTag(courseList.get(i).getCourseID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = false; //강의추가가능여부
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의가 삭제되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    courseList.remove(i);
                                    notifyDataSetChanged();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의 삭제에 실패하였습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    String courseIndexString = String.valueOf(courseList.get(i).getCourseIndex());
                    DeleteCourseRequest deleteRequest = new DeleteCourseRequest(userID, courseIndexString, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(deleteRequest);
            }
        });
        return v;

    }

}

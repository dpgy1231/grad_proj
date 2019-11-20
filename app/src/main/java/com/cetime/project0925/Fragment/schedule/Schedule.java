package com.cetime.project0925.Fragment.schedule;

import android.content.Context;

import com.cetime.project0925.*;
import com.cetime.project0925.reference.AutoResizeTextView;

public class Schedule {//29
    private String monday[] = new String[37];
    private String tuesday[] = new String[37];
    private String wednesday[] = new String[37];
    private String thursday[] = new String[37];
    private String friday[] = new String[37];
    private int time;

    public Schedule(){
        for(int i=0; i<37; i++){
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }


    public void addSchedule(String scheduleText) {   //파싱
        int temp;

        if((temp=scheduleText.indexOf("월")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            monday[timeArray[j]]="수업";
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("화")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            tuesday[timeArray[j]]="수업";
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("수")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            wednesday[timeArray[j]]="수업";
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("목")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            thursday[timeArray[j]]="수업";
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("금")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            friday[timeArray[j]]="수업";
                        }
                    }
                }
            }
        }
    }

    public boolean validate(String scheduleText){
        if(scheduleText.equals("")){
            return true;
        }
        int temp;
        if((temp=scheduleText.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            if(!monday[timeArray[j]].equals("")){
                                return false;
                            }
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            if(!tuesday[timeArray[j]].equals("")){
                                return false;
                            }
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            if(!wednesday[timeArray[j]].equals("")){
                                return false;
                            }
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            if(!thursday[timeArray[j]].equals("")){
                                return false;
                            }
                        }
                    }
                }

            }
        }
        if((temp=scheduleText.indexOf("금")) > -1){
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            if(!friday[timeArray[j]].equals("")){
                                return false;
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor) {
        int temp;
        String professor;
//        if (courseProfessor.equals("")) {
            professor = "";
//        } else {
//            professor = "(" + courseProfessor + ")";
//      }
        if ((temp = scheduleText.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) { //[와 ]의 위치를 찾아 각각 startpoint, endpoint에 저장
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            monday[timeArray[j]] = courseTitle + professor;
                        }
                    }
                }

            }
        }
        if ((temp = scheduleText.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            tuesday[timeArray[j]] = courseTitle + professor;
                        }
                    }
                }

            }
        }
        if ((temp = scheduleText.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            wednesday[timeArray[j]] = courseTitle + professor;
                        }
                    }
                }

            }
        }
        if ((temp = scheduleText.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            thursday[timeArray[j]] = courseTitle + professor;
                        }
                    }
                }

            }
        }
        if ((temp = scheduleText.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;

                    int[] timeArray;
                    time = Integer.parseInt(scheduleText.substring(startPoint+1, endPoint));
                    timeArray = transTime(time);

                    for(int j=0; j<37; j++){
                        if(timeArray[j] != 0){
                            friday[timeArray[j]] = courseTitle + professor;
                        }
                    }
                }
            }
        }
    }

    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context){
        int maxLength = 0;
        String maxString = "";
        for(int i=1; i<37; i++){
            if(this.monday[i].length() > maxLength){
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }
            if(this.tuesday[i].length() > maxLength){
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if(this.wednesday[i].length() > maxLength){
                maxLength = this.wednesday[i].length();
                maxString = this.wednesday[i];
            }
            if(this.thursday[i].length() > maxLength){
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if(this.friday[i].length() > maxLength){
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }
        }

        for(int i=1; i< 37; i++){
            if(!this.monday[i].equals("")){
                if(i%4==0) monday[i].setText(this.monday[i]);
                else monday[i].setText("");
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colormediumorange));
            }
            else {
                monday[i].setText(maxString);
            }

            if(!this.tuesday[i].equals("")){
                if(i%4==0) tuesday[i].setText(this.tuesday[i]);
                else tuesday[i].setText("");
                tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colormediumorange));
            }
            else {
                tuesday[i].setText(maxString);
            }

            if(!this.wednesday[i].equals("")){
                if(i%4==0) wednesday[i].setText(this.wednesday[i]);
                else wednesday[i].setText("");
                wednesday[i].setBackgroundColor(context.getResources().getColor(R.color.colormediumorange));
            }
            else {
                wednesday[i].setText(maxString);
            }

            if(!this.thursday[i].equals("")){
                if(i%4==0)  thursday[i].setText(this.thursday[i]);
                else thursday[i].setText("");
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colormediumorange));
            }
            else {
                thursday[i].setText(maxString);
            }

            if(!this.friday[i].equals("")){
                if(i%4==0)  friday[i].setText(this.friday[i]);
                else friday[i].setText("");
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colormediumorange));
            }
            else {
                friday[i].setText(maxString);
            }
            monday[i].resizeText();
            tuesday[i].resizeText();
            wednesday[i].resizeText();
            thursday[i].resizeText();
            friday[i].resizeText();
        }
    }

    public int[] transTime(int time){
        int[] timeArray = new int[37];
        int cnt=0;
        for(int i=0; i<37; i++){
            timeArray[i] = 0;
        }
        switch(time){
            case 1:
                timeArray[cnt++]=1;
                timeArray[cnt++]=2;
                timeArray[cnt++]=3;
                timeArray[cnt++]=4;
                break;
            case 2:
                timeArray[cnt++]=5;
                timeArray[cnt++]=6;
                timeArray[cnt++]=7;
                timeArray[cnt++]=8;
                break;
            case 3:
                timeArray[cnt++]=9;
                timeArray[cnt++]=10;
                timeArray[cnt++]=11;
                timeArray[cnt++]=12;
                break;
            case 4:
                timeArray[cnt++]=13;
                timeArray[cnt++]=14;
                timeArray[cnt++]=15;
                timeArray[cnt++]=16;
                break;
            case 5:
                timeArray[cnt++]=17;
                timeArray[cnt++]=18;
                timeArray[cnt++]=19;
                timeArray[cnt++]=20;
                break;
            case 6:
                timeArray[cnt++]=21;
                timeArray[cnt++]=22;
                timeArray[cnt++]=23;
                timeArray[cnt++]=24;
                break;
            case 7:
                timeArray[cnt++]=25;
                timeArray[cnt++]=26;
                timeArray[cnt++]=27;
                timeArray[cnt++]=28;
                break;
            case 8:
                timeArray[cnt++]=29;
                timeArray[cnt++]=30;
                timeArray[cnt++]=31;
                timeArray[cnt++]=32;
                break;
            case 9:
                timeArray[cnt++]=33;
                timeArray[cnt++]=34;
                timeArray[cnt++]=35;
                timeArray[cnt++]=36;
                break;
            case 21:
                timeArray[cnt++]=1;
                timeArray[cnt++]=2;
                timeArray[cnt++]=3;
                timeArray[cnt++]=4;
                timeArray[cnt++]=5;
                break;
            case 22:
                timeArray[cnt++]=7;
                timeArray[cnt++]=8;
                timeArray[cnt++]=9;
                timeArray[cnt++]=10;
                timeArray[cnt++]=11;
                break;
            case 23:
                timeArray[cnt++]=13;
                timeArray[cnt++]=14;
                timeArray[cnt++]=15;
                timeArray[cnt++]=16;
                timeArray[cnt++]=17;
                break;
            case 24:
                timeArray[cnt++]=19;
                timeArray[cnt++]=20;
                timeArray[cnt++]=21;
                timeArray[cnt++]=22;
                timeArray[cnt++]=23;
                break;
            case 25:
                timeArray[cnt++]=25;
                timeArray[cnt++]=26;
                timeArray[cnt++]=27;
                timeArray[cnt++]=28;
                timeArray[cnt++]=29;
                break;
            case 26:
                timeArray[cnt++]=31;
                timeArray[cnt++]=32;
                timeArray[cnt++]=33;
                timeArray[cnt++]=34;
                timeArray[cnt++]=35;
                break;
        }

        return timeArray;
    }
}

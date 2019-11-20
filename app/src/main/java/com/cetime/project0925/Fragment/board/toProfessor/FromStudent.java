package com.cetime.project0925.Fragment.board.toProfessor;

import java.io.Serializable;

public class FromStudent implements Serializable {

    int index;
    String tag;
    String name;
    String title;
    String content;
    String writer;
    String date;
    String time;
    String userID;

    public FromStudent() {
    }

    public FromStudent(int index, String tag, String name, String title, String content, String writer, String date, String time, String userID) {
        this.index = index;
        this.tag = tag;
        this.name = name;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
        this.time = time;
        this.userID = userID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

package com.cetime.project0925.Fragment.board.notice;

import java.io.Serializable;

public class Notice implements Serializable {

    int index;
    String tag;
    String title;
    String content;
    String writer;
    String date;
    String time;
    int count;
    String userID;
    String file;

    public Notice() {
    }

    public Notice(int index, String tag, String title, String content, String writer, String date, String time, int count, String userID, String file) {
        this.index = index;
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
        this.time = time;
        this.count = count;
        this.userID = userID;
        this.file = file;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

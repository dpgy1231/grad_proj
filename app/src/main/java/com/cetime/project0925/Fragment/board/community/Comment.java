package com.cetime.project0925.Fragment.board.community;

import java.io.Serializable;

public class Comment implements Serializable {

    int index;
    int communityIndex;
    String tag;
    String writer;
    String content;
    String date;
    String time;
    String userID;

    public Comment() {
    }

    public Comment(int index, int communityIndex, String tag, String writer, String content, String date, String time, String userID) {
        this.index = index;
        this.communityIndex = communityIndex;
        this.tag = tag;
        this.writer = writer;
        this.content = content;
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

    public int getCommunityIndex() {
        return communityIndex;
    }

    public void setCommunityIndex(int communityIndex) {
        this.communityIndex = communityIndex;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

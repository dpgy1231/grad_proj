package com.cetime.project0925.Fragment.chat;

import java.io.Serializable;

public class ChatRoom implements Serializable {

    int index;
    String yourID;
    String yourName;
    String content;
    String date;
    String time;

    public ChatRoom() {
    }

    public ChatRoom(int index, String yourID, String yourName, String content, String date, String time) {
        this.index = index;
        this.yourID = yourID;
        this.yourName = yourName;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getYourID() {
        return yourID;
    }

    public void setYourID(String yourID) {
        this.yourID = yourID;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}

package com.cetime.project0925.Fragment.chat;

import java.io.Serializable;

public class Chat implements Serializable {

    int index;
    String ID1;
    String name1;
    String ID2;
    String name2;
    String content;
    String date;
    String time;
    int count;

    public Chat() {
    }

    public Chat(int index, String ID1, String name1, String ID2, String name2, String content, String date, String time, int count) {
        this.index = index;
        this.ID1 = ID1;
        this.name1 = name1;
        this.ID2 = ID2;
        this.name2 = name2;
        this.content = content;
        this.date = date;
        this.time = time;
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getID1() {
        return ID1;
    }

    public void setID1(String ID1) {
        this.ID1 = ID1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getID2() {
        return ID2;
    }

    public void setID2(String ID2) {
        this.ID2 = ID2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package com.cetime.project0925.Fragment.CEinfo;

import java.io.Serializable;

public class Calendar implements Serializable {

    int index;
    String content;
    int year;
    int month;
    int date;

    public Calendar() {
    }

    public Calendar(int index, String content, int year, int month, int date) {
        this.index = index;
        this.content = content;
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}

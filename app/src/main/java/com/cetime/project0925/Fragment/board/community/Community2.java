package com.cetime.project0925.Fragment.board.community;

import java.io.Serializable;

public class Community2 implements Serializable {

    int index;
    String heartUserID;

    public Community2() {
    }

    public Community2(int index, String heartUserID) {
        this.index = index;
        this.heartUserID = heartUserID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHeartUserID() {
        return heartUserID;
    }

    public void setHeartUserID(String heartUserID) {
        this.heartUserID = heartUserID;
    }
}

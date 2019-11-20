package com.cetime.project0925.Fragment.board.community;

import java.io.Serializable;

public class Heart implements Serializable {
    int index;
    int boardIndex;
    String userID;

    public Heart() {
    }

    public Heart(int index, int boardIndex, String userID) {
        this.index = index;
        this.boardIndex = boardIndex;
        this.userID = userID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getBoardIndex() {
        return boardIndex;
    }

    public void setBoardIndex(int boardIndex) {
        this.boardIndex = boardIndex;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

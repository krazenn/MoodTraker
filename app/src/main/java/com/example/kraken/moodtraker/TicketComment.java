package com.example.kraken.moodtraker;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.kraken.moodtraker.MainActivity.BUNDLE_COMMENT;

public class TicketComment {

    String comment;
    int theme;
    Date date;
    List <TicketComment> listTicketComment;


    public List<TicketComment> getListTicketComment() {
        return listTicketComment;
    }

    public void setListTicketComment(List<TicketComment> listTicketComment) {
        this.listTicketComment = listTicketComment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

}


package com.example.kraken.moodtraker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.kraken.moodtraker.controller.DateTicket;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ListTicketComment {

    public static final String BUNDLE_TEMP_MOOD = "BUNDLE_TEMP_MOOD";
    public static final String BUNDLE_COMMENT = "BUNDLE_COMMENT";

    private final Context context;
    private final SharedPreferences sharedPref;
    private final SharedPreferences sharedPrefTemp;
    List<TicketComment> listTicketComment;
    Gson gson = new Gson();
    DateTicket dateTicket = new DateTicket();

    public ListTicketComment(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(BUNDLE_COMMENT, 0);
        sharedPrefTemp = context.getSharedPreferences(BUNDLE_TEMP_MOOD, 0);
    }

    public List<TicketComment> loadList() {
        String json = sharedPref.getString(BUNDLE_COMMENT, "");
        Type type = new TypeToken<ArrayList<TicketComment>>() {
        }.getType();
        listTicketComment = gson.fromJson(json, type);
        return listTicketComment;
    }

    public List<TicketComment> getListTicketComment() {
        return listTicketComment;
    }

    public void setListTicketComment(List<TicketComment> listTicketComment) {
        this.listTicketComment = listTicketComment;
    }

    public void saveList() {
        String ticketComments = gson.toJson(listTicketComment);
        sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
    }

    public void saveThemeTemp(int currentTheme) {
        sharedPrefTemp.edit().putInt(BUNDLE_TEMP_MOOD, currentTheme).apply();

    }

    public Integer loadTheme() {
        int currentTheme = sharedPrefTemp.getInt(BUNDLE_TEMP_MOOD, 0);
        return currentTheme;
    }

    public void autoSaveList(TicketComment lastTicketComment) {
        if (lastTicketComment.getDate() != null) {
            lastTicketComment = listTicketComment.get(listTicketComment.size() - 1);
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate()) == false) {

                lastTicketComment.setTheme(sharedPrefTemp.getInt(BUNDLE_TEMP_MOOD, 0));
                lastTicketComment.setDate(lastTicketComment.getDate());
                lastTicketComment.setComment(lastTicketComment.getComment());
                listTicketComment.remove(listTicketComment.size() - 1);
                listTicketComment.add(lastTicketComment);
                String ticketComments = gson.toJson(listTicketComment);
                sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();

            }
        }
    }

    public void addTicketCommentToList(TicketComment ticketComment) {
        listTicketComment.add(ticketComment);
    }
}


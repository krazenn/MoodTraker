package com.example.kraken.moodtraker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kraken.moodtraker.controller.DateTicket;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.kraken.moodtraker.controller.MainActivity.currentTheme;


public class ListTicketComment {

    public static final String BUNDLE_TEMP_MOOD = "BUNDLE_TEMP_MOOD";
    public static final String BUNDLE_COMMENT = "BUNDLE_COMMENT";

    private final Context context;
    private final SharedPreferences sharedPref;
    private final SharedPreferences sharedPrefTemp;
    List<TicketComment> listTicketComment;
    Gson gson = new Gson();
    DateTicket dateTicket;
    public ListTicketComment(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(BUNDLE_COMMENT, 0);
        sharedPrefTemp = context.getSharedPreferences(BUNDLE_TEMP_MOOD, 0);
    }

    /**
     * Load list from SharedPreferences
     *
     * @return list TicketComment
     */
    public List<TicketComment> loadList() {
        String json = sharedPref.getString(BUNDLE_COMMENT, "");
        Type type = new TypeToken<ArrayList<TicketComment>>() {
        }.getType();
        listTicketComment = gson.fromJson(json, type);
        return listTicketComment;
    }
    public void compareDate(Date lastTicketCommentDate) {
            dateTicket = new DateTicket();
        if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketCommentDate)) {
                Log.d("date1", gson.toJson(dateTicket.getCurrentDate()));
                Log.d("date2", gson.toJson(lastTicketCommentDate));
                listTicketComment.remove(listTicketComment.size() - 1);
            }
        }


    /**
     * Save list in SharedPreferences
     */
    public void saveList() {
        String ticketComments = gson.toJson(listTicketComment);
        sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
    }

    /**
     * Save current theme in SharedPreferences
     */
    public void saveThemeTemp() {
        sharedPrefTemp.edit().putInt(BUNDLE_TEMP_MOOD, currentTheme).apply();
        Log.d("themetemp",gson.toJson(sharedPref.getInt(BUNDLE_TEMP_MOOD,currentTheme)));
    }
    public Integer loadThemeTemp(){

        int themeTemp = sharedPrefTemp.getInt(BUNDLE_TEMP_MOOD, 0);
        return themeTemp;
    }

    public void loadStartTheme(ImageView imageView, RelativeLayout relativeLayout, TicketComment lastTicketComment){
        if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())){
            loadLastTheme(imageView , relativeLayout, loadThemeTemp());
        }else {
            loadLastTheme(imageView , relativeLayout, 0);
        }
    }

    public void loadLastTheme(ImageView imageView, RelativeLayout relativeLayout, int currentTheme){
        MoodTheme moodTheme = new MoodTheme();
        imageView.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        relativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    /**
     * save the new mood selected in the day  if no save the day before
     * @param lastTicketComment the last ticket comment entry
     */
    public TicketComment autoSaveList(TicketComment lastTicketComment) {
        if (lastTicketComment!= null) {
            lastTicketComment = listTicketComment.get(listTicketComment.size() - 1);
            dateTicket = new DateTicket();
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
        return lastTicketComment;
    }

    /**
     * add TicketComment to list
     * @param ticketComment last entry user
     */
    public void addTicketCommentToList(TicketComment ticketComment) {
        listTicketComment.add(ticketComment);
    }
}


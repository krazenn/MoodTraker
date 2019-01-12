package com.example.kraken.moodtraker.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    TicketComment ticketComment;
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
        if (listTicketComment == null){
            listTicketComment = new ArrayList<>();
        }

        return listTicketComment;
    }

    /**
     * Compare lastTicketComment date and current date
     *
     * @param lastTicketCommentDate Last ticket comment in list
     */
    public void compareDate(Date lastTicketCommentDate) {
            dateTicket = new DateTicket();
        if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketCommentDate)) {
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
        dateTicket = new DateTicket();
        //set date ticket if null
        if (lastTicketComment == null){
            lastTicketComment = new TicketComment();
            lastTicketComment.setComment("");
            lastTicketComment.setDate(dateTicket.getCurrentDate());
        }
        if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())){
            loadLastTheme(imageView , relativeLayout, loadThemeTemp());
        }else {
            loadLastTheme(imageView , relativeLayout, 0);
        }
    }

    private void loadLastTheme(ImageView imageView, RelativeLayout relativeLayout, int currentTheme){
        MoodTheme moodTheme = new MoodTheme();
        imageView.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        relativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    /**
     * save the new mood selected in the day  if no save the day before
     * @param lastTicketComment the last ticket comment entry
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TicketComment autoSaveList(TicketComment lastTicketComment) {
        dateTicket = new DateTicket();
        if (listTicketComment.size() > 0) {
            lastTicketComment = listTicketComment.get(listTicketComment.size() - 1);
        }else {
                lastTicketComment.setComment("");
                lastTicketComment.setDate(dateTicket.getCurrentDate());

        }
        if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate()) == false) {

                lastTicketComment.setTheme(sharedPrefTemp.getInt(BUNDLE_TEMP_MOOD, 0));
                lastTicketComment.setDate(lastTicketComment.getDate());
                lastTicketComment.setComment(lastTicketComment.getComment());
                listTicketComment.remove(listTicketComment.size() - 1);
                listTicketComment.add(lastTicketComment);

                //period difference number day for create default ticket for day app not open
                long periodDay = ChronoUnit.DAYS.between(dateTicket.convertToLocalDateViaInstant(dateTicket.getCurrentDate()), dateTicket.convertToLocalDateViaInstant(lastTicketComment.getDate()));

                //create ticket for day not open
                while (periodDay < 0) {
                    Log.d("period", gson.toJson(periodDay));
                    listTicketComment.add(defaultTicketComment());
                    lastTicketComment = listTicketComment.get(listTicketComment.size()-1);
                    periodDay++;
                }
                String ticketComments = gson.toJson(listTicketComment);
                sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
            }

        return lastTicketComment;
    }


    public TicketComment defaultTicketComment() {
        ticketComment = new TicketComment();
        ticketComment.setComment("");
        ticketComment.setTheme(0);
        ticketComment.setDate(dateTicket.getCurrentDate());
        return ticketComment;
    }

    /**
     * add TicketComment to list
     * @param ticketComment last entry user
     */
    public void addTicketCommentToList(TicketComment ticketComment) {
        listTicketComment.add(ticketComment);
    }
}


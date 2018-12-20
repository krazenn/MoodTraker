package com.example.kraken.moodtraker.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.Swipe;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    public static final String BUNDLE_TEMP_MOOD = "BUNDLE_TEMP_MOOD";

    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewSmiley;
    static int currentTheme = 0;
    private String comment;
    private EditText mEditTextComment;

    public static final String BUNDLE_COMMENT = "BUNDLE_COMMENT";
    DateTicket dateTicket = new DateTicket();
    List<TicketComment> mTicketCommentList;
    Gson gson = new Gson();
    TicketComment ticketComment;
    MoodTheme moodTheme = new MoodTheme();
    private SharedPreferences sharedPrefTemp;


    public static int getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(int currentTheme) {
        MainActivity.currentTheme = currentTheme;
    }


    public void nextMoodTheme() {
        currentTheme = currentTheme % (moodTheme.getListSmileyImage().length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences(BUNDLE_COMMENT, MODE_PRIVATE);
        sharedPrefTemp = getSharedPreferences(BUNDLE_TEMP_MOOD, MODE_PRIVATE);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);

        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);

        loadList();
        loadTheme();
        autoSave();
        mImageViewSmiley.setOnTouchListener(new Swipe(MainActivity.this) {
            public void onSwipeTop() {
                currentTheme++;
                nextMoodTheme();
                sharedPrefTemp.edit().putInt(BUNDLE_TEMP_MOOD, currentTheme).apply();
            }

            public void onSwipeBottom() {
                if (currentTheme == 0) {
                    currentTheme = 4;
                    nextMoodTheme();
                } else {
                    currentTheme = currentTheme - 1;
                }
                nextMoodTheme();
                sharedPrefTemp.edit().putInt(BUNDLE_TEMP_MOOD, currentTheme).apply();
            }
        });


        imageButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogComment();
            }
        });
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }

    public void loadTheme() {
        if (ticketComment != null) {
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), ticketComment.getDate())) {
                currentTheme = sharedPrefTemp.getInt(BUNDLE_TEMP_MOOD, 0);
            } else {
                currentTheme = 0;
            }
        }
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    public void alertDialogComment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_comment, null);
        mEditTextComment = v.findViewById(R.id.inputComment);
        if (ticketComment.getDate() != null) {
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), ticketComment.getDate())) {
                mEditTextComment.setHint(ticketComment.getComment());
            }
        }
        builder.setView(v);
        builder.setTitle("Commentaires:")
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment = mEditTextComment.getText().toString();
                        createTicketComment();
                        compareDate();
                        saveList();
                        Log.d("enregistrer", gson.toJson(mTicketCommentList));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();

        builder.show();
    }

    public void createTicketComment() {
        ticketComment = new TicketComment();
        ticketComment.setComment(comment);
        ticketComment.setTheme(currentTheme);
        ticketComment.setDate(dateTicket.getCurrentDate());

    }
    public void saveList() {
        mTicketCommentList.add(ticketComment);
        String ticketComments = gson.toJson(mTicketCommentList);
        sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
    }

    public void loadList() {

        ticketComment = new TicketComment();
        String json = sharedPref.getString(BUNDLE_COMMENT, "");
        Type type = new TypeToken<ArrayList<TicketComment>>() {
        }.getType();
        mTicketCommentList = gson.fromJson(json, type);
        if (mTicketCommentList != null) {
            ticketComment = mTicketCommentList.get(mTicketCommentList.size() - 1);
        } else {
            mTicketCommentList = new ArrayList<>();
        }
    }

    public void compareDate() {

        if (mTicketCommentList.size() != 0) {

            if (dateTicket.compareDate(dateTicket.getCurrentDate(), ticketComment.getDate())) {
                mTicketCommentList.remove(mTicketCommentList.size() - 1);
            } else {
                mTicketCommentList.add(ticketComment);
            }
        }
    }

    public void autoSave() {

        if (ticketComment.getDate() != null) {
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), ticketComment.getDate()) == false) {
                saveList();
            }
        }
    }
}

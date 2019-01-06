package com.example.kraken.moodtraker.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.kraken.moodtraker.ListTicketComment;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.Swipe;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewSmiley;
    public static int currentTheme = 0;
    private String comment;
    private EditText mEditTextComment;
    DateTicket dateTicket = new DateTicket();
    ListTicketComment listTicketComment;
    Gson gson = new Gson();
    TicketComment ticketComment;
    TicketComment lastTicketComment;
    MoodTheme moodTheme = new MoodTheme();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);

        listTicketComment = new ListTicketComment(this);


        if (listTicketComment.loadList().size() > 0){
            lastTicketComment = listTicketComment.loadList().get(listTicketComment.loadList().size()-1);
        }else {
            lastTicketComment = new TicketComment();
            lastTicketComment.setDate(dateTicket.getCurrentDate());
        }
        listTicketComment.autoSaveList(lastTicketComment);
        listTicketComment.loadStartTheme(mImageViewSmiley,mRelativeLayout,lastTicketComment);
        if (!dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())){
            currentTheme = 0;
        }

        //Load setting Swipe
        loadSwipe();

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
    public void nextMoodTheme() {
        currentTheme = currentTheme % (moodTheme.getListSmileyImage().length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    public void alertDialogComment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_comment, null);
        mEditTextComment = v.findViewById(R.id.inputComment);
        if (listTicketComment.loadList().size() > 0) {
            lastTicketComment = listTicketComment.loadList().get(listTicketComment.loadList().size() - 1);
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())) {
                mEditTextComment.setHint(lastTicketComment.getComment());
                if (lastTicketComment.getComment().equals("")) {
                    mEditTextComment.setHint("Saisir un commentaire:   ");
                }
            } else {
                mEditTextComment.setHint("Saisir un commentaire:");
            }
        }
        builder.setView(v);
        builder.setTitle("Commentaires:")
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment = mEditTextComment.getText().toString();
                        dateTicket = new DateTicket();
                        createTicketComment();
                        if (listTicketComment.loadList().size() > 0) {
                            listTicketComment.compareDate(lastTicketComment.getDate());
                        }
                        listTicketComment.addTicketCommentToList(ticketComment );
                        listTicketComment.saveList();
                        Log.d("enregistrer", gson.toJson(listTicketComment.loadList()));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();

        builder.show();
    }
//Create Ticket Comment
    public TicketComment createTicketComment() {
        ticketComment = new TicketComment();
        ticketComment.setComment(comment);
        ticketComment.setTheme(currentTheme);
        ticketComment.setDate(dateTicket.getCurrentDate());
        return ticketComment;
    }
//Play sound from current mood
    public void playSoud()  {
        mediaPlayer = MediaPlayer.create(MainActivity.this, moodTheme.getListNoteMusic()[currentTheme]);
        mediaPlayer.start();
    }

//unload memory when application destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseSound();
    }
    /*SWIPE COLOR BACKGROUND AND SMILEY UP/DOWN
PLAY NOTE MUSIC WHEN SWIPE
SAVE MOOD IN SHARED PREFERENCES */
    @SuppressLint("ClickableViewAccessibility")
    public void loadSwipe(){
        mImageViewSmiley.setOnTouchListener(new Swipe(MainActivity.this) {
            //UP
            public void onSwipeTop() {
                releaseSound();
                currentTheme++;
                nextMoodTheme();
                    playSoud();
                listTicketComment.saveThemeTemp();
            }
            //DOWN
            public void onSwipeBottom() {
                releaseSound();
                if (currentTheme == 0) {
                    currentTheme = 4;
                    nextMoodTheme();
                } else {
                    currentTheme = currentTheme - 1;
                }
                nextMoodTheme();
                    playSoud();
                listTicketComment.saveThemeTemp();
            }
        });
    }
    public void releaseSound(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

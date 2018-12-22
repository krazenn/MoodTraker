package com.example.kraken.moodtraker.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kraken.moodtraker.ListTicketComment;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.Swipe;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewSmiley;
    static int currentTheme = 0;
    private String comment;
    private EditText mEditTextComment;
    DateTicket dateTicket = new DateTicket();
    Gson gson = new Gson();
    TicketComment ticketComment;
    TicketComment lastTicketComment;
    MoodTheme moodTheme = new MoodTheme();
    MediaPlayer mediaPlayer;
    ListTicketComment listTicketComment;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        listTicketComment = new ListTicketComment(this);
        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);

        listTicketComment.loadList();
        if (listTicketComment.getListTicketComment() != null) {
            lastTicketComment = listTicketComment.getListTicketComment().get(listTicketComment.getListTicketComment().size() - 1);
        }
        loadTheme();
        listTicketComment.autoSaveList(lastTicketComment);
        /*Play sound at start*/
        try {
            playSoud();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageViewSmiley.setOnTouchListener(new Swipe(MainActivity.this) {
            public void onSwipeTop() throws IOException {
                mediaPlayer.release();
                currentTheme++;
                nextMoodTheme();
                playSoud();
                listTicketComment.saveThemeTemp(currentTheme);
            }

            public void onSwipeBottom() throws IOException {
                mediaPlayer.release();
                if (currentTheme == 0) {
                    currentTheme = 4;
                    nextMoodTheme();
                } else {
                    currentTheme = currentTheme - 1;
                }
                nextMoodTheme();
                playSoud();
                listTicketComment.saveThemeTemp(currentTheme);

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
        if (lastTicketComment.getDate() != null) {
            if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())) {
                currentTheme = listTicketComment.loadTheme();
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
        if (lastTicketComment.getDate() != null) {
            lastTicketComment = listTicketComment.getListTicketComment().get(listTicketComment.getListTicketComment().size() - 1);
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
                        createTicketComment();
                        removeLastTicketCommentIfDateEqual();
                        listTicketComment.addTicketCommentToList(ticketComment);
                        listTicketComment.saveList();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();

        builder.show();
    }

    public void nextMoodTheme() {
        currentTheme = currentTheme % (moodTheme.getListSmileyImage().length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    public void createTicketComment() {
        ticketComment = new TicketComment(comment, currentTheme, dateTicket.getCurrentDate());
    }

    public void removeLastTicketCommentIfDateEqual() {
        if (listTicketComment.getListTicketComment().size() != 0) {
            lastTicketComment = listTicketComment.getListTicketComment().get(listTicketComment.getListTicketComment().size() - 1);
            Log.d("ticket", gson.toJson(lastTicketComment));

            if (dateTicket.compareDate(dateTicket.getCurrentDate(), lastTicketComment.getDate())) {
                listTicketComment.getListTicketComment().remove(listTicketComment.getListTicketComment().size() - 1);
                Log.d("date", gson.toJson(dateTicket.getCurrentDate()));
            }
        }
    }

    public void playSoud() throws IOException {
        mediaPlayer = MediaPlayer.create(MainActivity.this, moodTheme.getListNoteMusic()[currentTheme]);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


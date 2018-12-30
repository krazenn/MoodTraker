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

        //declare element
        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        listTicketComment = new ListTicketComment(this);
        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);

        //Load List
        listTicketComment.loadList();
        if (listTicketComment.getListTicketComment() != null) {
            lastTicketComment = listTicketComment.getListTicketComment().get(listTicketComment.getListTicketComment().size() - 1);
        }

        //Load last theme save in preference
        loadTheme();
        listTicketComment.autoSaveList(lastTicketComment);

        //Play sound from last theme mood
        try {
            playSoud();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Swipe for change mood slide up or down
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

        //Open Alertdialod when click on image button Comment
        imageButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogComment();
            }
        });

        //Start Activity History when click on image button History
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }

    /**
     * load last theme show
     */
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

    /**
     * AlertDialog contains Title, EditText and two Button
     * Button Enregistrer for save curent ticket comment
     * Button Annuler for close AlerDialog
     */
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
        //add title alertdialog
        builder.setTitle("Commentaires:")
                //add button for save comment and mood
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment = mEditTextComment.getText().toString();
                        createTicketComment();
                        removeLastTicketCommentIfDateEqual();
                        listTicketComment.addTicketCommentToList(ticketComment);
                        listTicketComment.saveList();
                    }
                })
                //add button for cancel
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();

        builder.show();
    }

    /**
     * Change the current theme (smiley and background color)
     */
    public void nextMoodTheme() {
        currentTheme = currentTheme % (moodTheme.getListSmileyImage().length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    /**
     * Create TicketComment
     */
    public void createTicketComment() {
        ticketComment = new TicketComment(comment, currentTheme, dateTicket.getCurrentDate());
    }

    /**
     * Remove the last ticket in list if Date are equal same day
     */
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

    /**
     * Play sound from moodTheme.getListNoteMusic
     * @throws IOException
     */
    public void playSoud() throws IOException {
        mediaPlayer = MediaPlayer.create(MainActivity.this, moodTheme.getListNoteMusic()[currentTheme]);
        mediaPlayer.start();
    }

    //for unload memory when app onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


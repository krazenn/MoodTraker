
        package com.example.kraken.moodtraker.controller;

        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
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
        import com.google.gson.reflect.TypeToken;

        import java.io.IOException;
        import java.lang.reflect.Type;
        import java.util.ArrayList;
        import java.util.List;

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

    public void nextMoodTheme() {
        currentTheme = currentTheme % (moodTheme.getListSmileyImage().length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);

        listTicketComment = new ListTicketComment(this);
        currentTheme = listTicketComment.loadThemeTemp();
        if (listTicketComment.loadList().size() > 0){
            lastTicketComment = listTicketComment.loadList().get(listTicketComment.loadList().size()-1);
        }
        listTicketComment.loadList();
        listTicketComment.autoSaveList(lastTicketComment);
        listTicketComment.loadStartTheme(mImageViewSmiley,mRelativeLayout,lastTicketComment);
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
               listTicketComment.saveThemeTemp();
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
                listTicketComment.saveThemeTemp();
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

    public void alertDialogComment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_comment, null);
        mEditTextComment = v.findViewById(R.id.inputComment);
        if (lastTicketComment.getDate() != null) {
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
                        listTicketComment.compareDate(lastTicketComment.getDate());
                        createTicketComment();
                        listTicketComment.addTicketCommentToList(ticketComment);
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

    public void createTicketComment() {
        ticketComment = new TicketComment();
        ticketComment.setComment(comment);
        ticketComment.setTheme(currentTheme);
        ticketComment.setDate(dateTicket.getCurrentDate());
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

package com.example.kraken.moodtraker;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewSmiley;
    static int currentTheme = 1;
    private String comment;
    private EditText mEditTextComment;
    DateFormat format = DateFormat.getTimeInstance();
    Calendar date = Calendar.getInstance(TimeZone.getDefault());
    final Date currentDate = date.getTime();

    public static final String BUNDLE_COMMENT = "BUNDLE_COMMENT";

    List<TicketComment> mTicketCommentList;
    Gson gson = new Gson();
    TicketComment ticketComment;
    MoodTheme moodTheme = new MoodTheme();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences(BUNDLE_COMMENT, MODE_PRIVATE);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        ImageButton imageButtonNext = findViewById(R.id.imageBtnNext);
        ImageButton imageButtonComment = findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory = findViewById(R.id.imageBtnHistory);
        loadList();
        currentTheme = ticketComment.getTheme();
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);

        saveAuto();

        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTheme++;
                nextMoodTheme();
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
        mEditTextComment.setText(ticketComment.getComment());
        builder.setView(v);
        builder.setTitle("Commentaires:")
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment = mEditTextComment.getText().toString();
                        compareDate();
                        saveList();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();
        builder.show();
    }

    public void saveList() {
        ticketComment = new TicketComment();
        ticketComment.setComment(comment);
        ticketComment.setTheme(currentTheme);
        ticketComment.setDate(currentDate);
        mTicketCommentList.add(ticketComment);
        String ticketComments = gson.toJson(mTicketCommentList);
        sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
        Log.d("list com", gson.toJson(mTicketCommentList));

    }

    public void loadList() {
        ticketComment = new TicketComment();
        String json = sharedPref.getString(BUNDLE_COMMENT, "");
        Type type = new TypeToken<ArrayList<TicketComment>>() {
        }.getType();
        mTicketCommentList = gson.fromJson(json, type);
        Log.d("ist main", gson.toJson(mTicketCommentList));

        if (mTicketCommentList != null) {
            ticketComment = mTicketCommentList.get(mTicketCommentList.size() - 1);
            Log.d("last", gson.toJson(ticketComment));
        } else {
            mTicketCommentList = new ArrayList<>();
        }
    }

    public void compareDate() {
        if (ticketComment.getDate() == currentDate) {
            mTicketCommentList.remove(mTicketCommentList.size() - 1);
        }
    }

    public void saveAuto() {
        if (format.format(currentDate) == "00:00:00 AM") ;
        {
            mTicketCommentList.add(ticketComment);
            String ticketComments = gson.toJson(mTicketCommentList);
            sharedPref.edit().putString(BUNDLE_COMMENT, ticketComments).apply();
            Log.d("date", format.format(currentDate));
        }
    }

}

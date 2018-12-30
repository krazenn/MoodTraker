package com.example.kraken.moodtraker.controller;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kraken.moodtraker.ListTicketComment;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RelativeLayout rLTicketHistoryDay1;
    RelativeLayout layoutTicket;
    LinearLayout mainLayout;
    LinearLayout linearLayoutComment;
    RelativeLayout rLTicketHistoryDay5;
    RelativeLayout rLTicketHistoryDay6;
    RelativeLayout rLTicketHistoryDay7;
    ImageView mImageViewIconeComment1;
    ImageView mImageViewIconeComment2;
    ImageView mImageViewIconeComment3;
    ImageView mImageViewIconeComment4;
    ImageView mImageViewIconeComment5;
    ImageView mImageViewIconeComment6;
    ImageView mImageViewIconeComment7;
    Button buttonDay1 ;
    Button buttonDay2 ;
    Button buttonDay3 ;
    Button buttonDay4 ;
    Button buttonDay5 ;
    Button buttonDay6 ;
    Button buttonDay7 ;
    String commentday1;
    String commentday2;
    String commentday3;
    String commentday4;
    String commentday5;
    String commentday6;
    String commentday7;

    private SharedPreferences sharedPref ;
    List<TicketComment> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mainLayout = findViewById(R.id.mainLayout);


        LayoutInflater inflater = HistoryActivity.this.getLayoutInflater();
        View inflate = inflater.inflate(R.layout.ticket_comment, null);
        rLTicketHistoryDay1 = inflate.findViewById(R.id.relativeLayoutTicketComment1);
        layoutTicket = inflate.findViewById(R.id.layoutTicket);

        linearLayoutComment = inflate.findViewById(R.id.linearLayoutComment1);
        final RecyclerView rv = findViewById(R.id.list_ticket_recycler_view);

        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(new MyAdapter());


        buttonDay1 = inflate.findViewById(R.id.buttonDay1);


        final Gson gson = new Gson();

        MoodTheme moodTheme = new MoodTheme();
        ListTicketComment listTicketComment = new ListTicketComment(this);



        TicketComment ticketCommentHistory;
        int nbDay = 7;
        if (listComment == null) {
            listComment = new ArrayList<>();
        }
        int i = 1;
        while (i < nbDay) {
        if (listComment.size()>0) {
            ticketCommentHistory = listComment.get(listComment.size() - i);
            //Create new TicketLayout
            layoutTicket = new RelativeLayout(this);

            linearLayoutComment = new LinearLayout(this);

            rLTicketHistoryDay1 = new RelativeLayout(this);
            rLTicketHistoryDay1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay1.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);

            rLTicketHistoryDay1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay1.setVisibility(View.VISIBLE);
            rLTicketHistoryDay1.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday1 = gson.toJson(ticketCommentHistory.getComment());
            mainLayout.addView(rLTicketHistoryDay1);
            mainLayout.addView(linearLayoutComment);
            mainLayout.addView(layoutTicket);

            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment1.setVisibility(View.INVISIBLE);
                buttonDay1.setVisibility(View.INVISIBLE);
            }else {
                buttonDay1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),commentday1,Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
            i++;
        }
    }
}







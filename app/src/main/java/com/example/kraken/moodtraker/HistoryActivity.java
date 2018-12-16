package com.example.kraken.moodtraker;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RelativeLayout rLTicketHistoryDay1;
    RelativeLayout rLTicketHistoryDay2;
    RelativeLayout rLTicketHistoryDay3;
    RelativeLayout rLTicketHistoryDay4;
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

    private SharedPreferences sharedPref ;
    List<TicketComment> listComment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rLTicketHistoryDay1 = findViewById(R.id.relativeLayoutTicketComment1);
        rLTicketHistoryDay2 = findViewById(R.id.relativeLayoutTicketComment2);
        rLTicketHistoryDay3 = findViewById(R.id.relativeLayoutTicketComment3);
        rLTicketHistoryDay4 = findViewById(R.id.relativeLayoutTicketComment4);
        rLTicketHistoryDay5 = findViewById(R.id.relativeLayoutTicketComment5);
        rLTicketHistoryDay6 = findViewById(R.id.relativeLayoutTicketComment6);
        rLTicketHistoryDay7 = findViewById(R.id.relativeLayoutTicketComment7);
        mImageViewIconeComment1 = findViewById(R.id.imgViewIconeComment1);
        mImageViewIconeComment2 = findViewById(R.id.imgViewIconeComment2);
        mImageViewIconeComment3 = findViewById(R.id.imgViewIconeComment3);
        mImageViewIconeComment4 = findViewById(R.id.imgViewIconeComment4);
        mImageViewIconeComment5 = findViewById(R.id.imgViewIconeComment5);
        mImageViewIconeComment6 = findViewById(R.id.imgViewIconeComment6);
        mImageViewIconeComment7 = findViewById(R.id.imgViewIconeComment7);
        TicketComment ticketCommentHistory1;
        TicketComment ticketCommentHistory2;
        TicketComment ticketCommentHistory3;
        TicketComment ticketCommentHistory4;
        TicketComment ticketCommentHistory5;
        TicketComment ticketCommentHistory6;
        TicketComment ticketCommentHistory7;
        MoodTheme moodTheme = new MoodTheme();
        sharedPref = getSharedPreferences("BUNDLE_COMMENT",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("BUNDLE_COMMENT", "");
        Type type = new TypeToken<ArrayList<TicketComment>>(){}.getType();
        listComment = gson.fromJson(json, type);
        Log.d("lst", gson.toJson(listComment));

        if (listComment == null) {
            listComment = new ArrayList<>();
        }
        ticketCommentHistory1 = listComment.get(listComment.size()-1);
        ticketCommentHistory2 = listComment.get(listComment.size()-2);


        if (listComment.size()-1 >=0) {

            Log.d("1", gson.toJson(listComment.get(listComment.size()-1)));
            rLTicketHistoryDay1.setVisibility(View.VISIBLE);
            rLTicketHistoryDay1.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory1.getTheme()]);
        }
            if (ticketCommentHistory1.getComment().equals("")){
                mImageViewIconeComment1.setVisibility(View.INVISIBLE);
            }


        if (listComment.size()-1 >=1) {

            Log.d("2", gson.toJson(listComment.get(listComment.size()-2)));
            rLTicketHistoryDay2.setVisibility(View.VISIBLE);
            rLTicketHistoryDay2.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory2.getTheme()]);
        }
            if (ticketCommentHistory2.getComment().equals("")){
                mImageViewIconeComment2.setVisibility(View.INVISIBLE);

        }

        if (listComment.size()-1 >=2){
            ticketCommentHistory3 = listComment.get(listComment.size()-3);
            rLTicketHistoryDay3.setVisibility(View.VISIBLE);
            rLTicketHistoryDay3.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory3.getTheme()]);
            if (ticketCommentHistory3.getComment().equals("")){
                mImageViewIconeComment3.setVisibility(View.INVISIBLE);
            }
        }

        if (listComment.size()-1 >=3){
            ticketCommentHistory4 = listComment.get(listComment.size()-4);
            rLTicketHistoryDay4.setVisibility(View.VISIBLE);
            rLTicketHistoryDay4.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory4.getTheme()]);
            if (ticketCommentHistory4.getComment().equals("")){
                mImageViewIconeComment4.setVisibility(View.INVISIBLE);
            }
        }

        if (listComment.size()-1 >=4){
            ticketCommentHistory5 = listComment.get(listComment.size()-5);
            rLTicketHistoryDay5.setVisibility(View.VISIBLE);
            rLTicketHistoryDay5.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory5.getTheme()]);
            if (ticketCommentHistory5.getComment().equals("")){
                mImageViewIconeComment5.setVisibility(View.INVISIBLE);
            }
        }

        if (listComment.size()-1 >=5){
            ticketCommentHistory6 = listComment.get(listComment.size()-6);
            rLTicketHistoryDay6.setVisibility(View.VISIBLE);
            rLTicketHistoryDay6.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory6.getTheme()]);
            if (ticketCommentHistory6.getComment().equals("")){
                mImageViewIconeComment6.setVisibility(View.INVISIBLE);
            }
        }

        if (listComment.size()-1 >=6){
            ticketCommentHistory7 = listComment.get(listComment.size()-7);

            rLTicketHistoryDay7.setVisibility(View.VISIBLE);
            rLTicketHistoryDay7.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory7.getTheme()]);
            if (ticketCommentHistory7.getComment().equals("")){
                mImageViewIconeComment7.setVisibility(View.INVISIBLE);
            }
        }

    }
}







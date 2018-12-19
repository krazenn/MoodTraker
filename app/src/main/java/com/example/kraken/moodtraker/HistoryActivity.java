package com.example.kraken.moodtraker;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

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
    Button buttonDay1 ;
    Button buttonDay2 ;
    Button buttonDay3 ;
    Button buttonDay4 ;
    Button buttonDay5 ;
    Button buttonDay6 ;
    Button buttonDay7 ;



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
        buttonDay1 = findViewById(R.id.buttonDay1);
        buttonDay2 = findViewById(R.id.buttonDay2);
        buttonDay3 = findViewById(R.id.buttonDay3);
        buttonDay4 = findViewById(R.id.buttonDay4);
        buttonDay5 = findViewById(R.id.buttonDay5);
        buttonDay6 = findViewById(R.id.buttonDay6);
        buttonDay7 = findViewById(R.id.buttonDay7);



        MoodTheme moodTheme = new MoodTheme();
        sharedPref = getSharedPreferences("BUNDLE_COMMENT",MODE_PRIVATE);
        final Gson gson = new Gson();
        String json = sharedPref.getString("BUNDLE_COMMENT", "");
        Type type = new TypeToken<ArrayList<TicketComment>>(){}.getType();
        listComment = gson.fromJson(json, type);
        Log.d("lst", gson.toJson(listComment));
        final String commentday1;
        final String commentday2;
        final String commentday3;
        final String commentday4;
        final String commentday5;
        final String commentday6;
        final String commentday7;
        TicketComment ticketCommentHistory;
        if (listComment == null) {
            listComment = new ArrayList<>();
        }

        if (listComment.size()>0) {
            ticketCommentHistory = listComment.get(listComment.size()-1);
            Log.d("1", gson.toJson(ticketCommentHistory));
            rLTicketHistoryDay1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay1.setVisibility(View.VISIBLE);
            rLTicketHistoryDay1.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday1 = gson.toJson(ticketCommentHistory.getComment());
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

        if (listComment.size()> 1) {
            ticketCommentHistory = listComment.get(listComment.size()-2);
            rLTicketHistoryDay2.setVisibility(View.VISIBLE);
            rLTicketHistoryDay2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay2.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday2 = gson.toJson(ticketCommentHistory.getComment());
            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment2.setVisibility(View.INVISIBLE);
                buttonDay2.setVisibility(View.INVISIBLE);
            }
                buttonDay2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),commentday2,Toast.LENGTH_LONG).show();
                    }
                });

        }

        if (listComment.size()>2){
            ticketCommentHistory = listComment.get(listComment.size()-3);
            rLTicketHistoryDay3.setVisibility(View.VISIBLE);
            rLTicketHistoryDay3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay3.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday3 = gson.toJson(ticketCommentHistory.getComment());
            Log.d("3", gson.toJson(ticketCommentHistory));
            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment3.setVisibility(View.INVISIBLE);
                buttonDay3.setVisibility(View.INVISIBLE);
            }else {
                buttonDay3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), commentday3,Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        if (listComment.size()>3){
            ticketCommentHistory = listComment.get(listComment.size()-4);
            rLTicketHistoryDay4.setVisibility(View.VISIBLE);
            rLTicketHistoryDay4.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay4.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday4 = gson.toJson(ticketCommentHistory.getComment());
            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment4.setVisibility(View.INVISIBLE);
                buttonDay4.setVisibility(View.INVISIBLE);
            }else {
                buttonDay4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), commentday4,Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        if (listComment.size()>4){
            ticketCommentHistory = listComment.get(listComment.size()-5);
            rLTicketHistoryDay5.setVisibility(View.VISIBLE);
            rLTicketHistoryDay5.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay5.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday5 = gson.toJson(ticketCommentHistory.getComment());
            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment5.setVisibility(View.INVISIBLE);
                buttonDay5.setVisibility(View.INVISIBLE);
            }else {
                buttonDay5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), commentday5,Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        if (listComment.size()>5) {
            ticketCommentHistory = listComment.get(listComment.size() - 6);
            rLTicketHistoryDay6.setVisibility(View.VISIBLE);
            rLTicketHistoryDay6.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay6.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday6 = gson.toJson(ticketCommentHistory.getComment());
            if (ticketCommentHistory.getComment().equals("")) {
                mImageViewIconeComment6.setVisibility(View.INVISIBLE);
                buttonDay6.setVisibility(View.INVISIBLE);
            } else {
                buttonDay6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), commentday6, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        if (listComment.size()>6){
            ticketCommentHistory = listComment.get(listComment.size()-7);
            rLTicketHistoryDay7.setVisibility(View.VISIBLE);
            rLTicketHistoryDay7.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentHistory.getTheme()]));
            rLTicketHistoryDay7.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentHistory.getTheme()]);
            commentday7 = gson.toJson(ticketCommentHistory.getComment());
            if (ticketCommentHistory.getComment().equals("")){
                mImageViewIconeComment7.setVisibility(View.INVISIBLE);
                buttonDay7.setVisibility(View.INVISIBLE);
            }else {
                buttonDay7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), commentday7,Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }
}







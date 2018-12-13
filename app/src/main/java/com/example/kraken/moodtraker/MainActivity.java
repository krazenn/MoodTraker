package com.example.kraken.moodtraker;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewSmiley;
    private int currentTheme;

    private int[] listSmileyImage = {R.drawable.smileyhappy, R.drawable.smileysuperhappy,R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal};
    private int[] listColorBackground = {R.color.light_sage, R.color.banana_yellow, R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.relativelayout);
        mImageViewSmiley = findViewById(R.id.imageViewSmiley);
        ImageButton imageButtonNext= findViewById(R.id.imageBtnNext);
        ImageButton imageButtonComment= findViewById(R.id.imageBtnComment);
        ImageButton imageButtonHistory= findViewById(R.id.imageBtnHistory);
        currentTheme = 1;




        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMoodTheme();
                currentTheme++;


            }
        });

        imageButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment alertComment = new AlertComment();
                alertComment.show(getSupportFragmentManager(),"comment");

            }
        });


    }

    public void nextMoodTheme(){
        MoodTheme moodTheme = new MoodTheme();
        currentTheme = currentTheme % (listSmileyImage.length);
        mImageViewSmiley.setImageResource(moodTheme.getListSmileyImage()[currentTheme]);
        mRelativeLayout.setBackgroundResource(moodTheme.getListColorBackground()[currentTheme]);
    }
}

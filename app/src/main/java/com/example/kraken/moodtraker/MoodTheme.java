package com.example.kraken.moodtraker;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MoodTheme {


    private int[] listSmileyImage = {R.drawable.smileyhappy, R.drawable.smileysuperhappy, R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal};
    private int[] listColorBackground = {R.color.light_sage, R.color.banana_yellow, R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65};

    public int[] getListSmileyImage() {
        return listSmileyImage;
    }

    public void setListSmileyImage(int[] listSmileyImage) {
        this.listSmileyImage = listSmileyImage;
    }

    public int[] getListColorBackground() {
        return listColorBackground;
    }

    public void setListColorBackground(int[] listColorBackground) {
        this.listColorBackground = listColorBackground;
    }
}


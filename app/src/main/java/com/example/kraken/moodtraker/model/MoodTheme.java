package com.example.kraken.moodtraker.model;

import android.media.MediaPlayer;

import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.controller.MainActivity;

public class MoodTheme {

    private int[] listSmileyImage = {R.drawable.smileyhappy, R.drawable.smileysuperhappy, R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal};
    private int[] listColorBackground = {R.color.light_sage, R.color.banana_yellow, R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65};
    private int[] listWeight = {4, 5, 1, 2, 3};
    private int[] listNoteMusic = {R.raw.g1, R.raw.f1, R.raw.d1, R.raw.c1, R.raw.a1};

    public int[] getListNoteMusic() {
        return listNoteMusic;
    }

    public int[] getListWeight() {
        return listWeight;
    }

    public int[] getListSmileyImage() {
        return listSmileyImage;
    }

    public int[] getListColorBackground() {
        return listColorBackground;
    }

}


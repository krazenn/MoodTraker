package com.example.kraken.moodtraker.model;

import com.example.kraken.moodtraker.R;

public class MoodTheme {

    //CONTENT ALL RESSOURCES FOR THEME SMILEY, COLOR FOR BACKGROUND, ARRAY OF WEIGHT, ARRAY OF NOTE MUSIC
    private int[] listSmileyImage = {R.drawable.smileyhappy, R.drawable.smileysuperhappy, R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal};
    private int[] listColorBackground = {R.color.light_sage, R.color.banana_yellow, R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65};

   //SET LAYOUT WIDTH DYNAMIC FROM MOOD
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


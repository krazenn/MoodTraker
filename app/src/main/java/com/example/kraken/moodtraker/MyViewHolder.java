package com.example.kraken.moodtraker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

class MyViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayoutTicket, relativeLayoutMood;
    LinearLayout linearLayoutTicket;
    Button button;
    ImageView imageView;
    TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        relativeLayoutMood = itemView.findViewById(R.id.layoutTicket);
        relativeLayoutTicket = itemView.findViewById(R.id.relativeLayoutTicketComment1);
        linearLayoutTicket = itemView.findViewById(R.id.linearLayoutComment1);
        button = itemView.findViewById(R.id.buttonDay1);
        imageView = itemView.findViewById(R.id.imgViewIconeComment1);
        textView = itemView.findViewById(R.id.textViewDayAgo1);
    }
}




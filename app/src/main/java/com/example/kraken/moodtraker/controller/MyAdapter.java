package com.example.kraken.moodtraker.controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<TicketComment> ticketCommentList ;

    public MyAdapter( List<TicketComment> ticketCommentList) {
        this.ticketCommentList = ticketCommentList;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ticket_comment, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder myViewHolder, int i) {
        MoodTheme moodTheme = new MoodTheme();
        myViewHolder.relativeLayoutMood.setBackgroundResource(moodTheme.getListColorBackground()[i]);

    }


    @Override
    public int getItemCount() {
        return ticketCommentList.size();
       }



class MyViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout relativeLayoutTicket, relativeLayoutMood;
    LinearLayout linearLayoutTicket;
    Button button;
    ImageView imageView;
    TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.relativeLayoutTicket = itemView.findViewById(R.id.layoutTicket);
        this.relativeLayoutMood = itemView.findViewById(R.id.relativeLayoutTicketComment1);
        this.linearLayoutTicket = itemView.findViewById(R.id.linearLayoutComment1);
        this.button = itemView.findViewById(R.id.buttonDay1);
        this.imageView = itemView.findViewById(R.id.imgViewIconeComment1);
        this.textView = itemView.findViewById(R.id.textViewDayAgo1);
    }
    }
}

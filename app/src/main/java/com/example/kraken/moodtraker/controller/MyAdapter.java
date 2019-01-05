package com.example.kraken.moodtraker.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.Day;
import com.example.kraken.moodtraker.model.MoodTheme;
import com.example.kraken.moodtraker.model.TicketComment;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<TicketComment> ticketCommentList ;
    Day day = new Day();

    public MyAdapter( List<TicketComment> ticketCommentList) {
        this.ticketCommentList = ticketCommentList;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        //CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ticket_comment, parent, false);
        //LIMIT NUMBER ITEMS SHOW HEIGHT / NBDAY
        int height = parent.getMeasuredHeight() / day.getNumberDay();
        int width = parent.getMeasuredWidth();
        listItem.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;

    }

    /**
     *
     * @param myViewHolder Content my items content in xml
     * @param position current position
     *                set weight, theme, visibility from TicketComment
     */
    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder myViewHolder, int position) {
        //UPDATE VIEWHOLDER FROM A TICKETCOMMENT CONTENT
        MoodTheme moodTheme = new MoodTheme();
        myViewHolder.textView.setText(day.getListDay()[position]);
        myViewHolder.relativeLayoutMood.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, moodTheme.getListWeight()[ticketCommentList.get(ticketCommentList.size()-position-1).getTheme()]));
        myViewHolder.relativeLayoutMood.setBackgroundResource(moodTheme.getListColorBackground()[ticketCommentList.get(ticketCommentList.size()-position-1).getTheme()]);

        if (ticketCommentList.get(ticketCommentList.size()-position-1).getComment().equals("")){
            myViewHolder.imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        //NUMBER DAY OF ITEMS RETURN
        if (ticketCommentList == null){
            ticketCommentList = new ArrayList<>();
        }
        return Math.min(ticketCommentList.size(), day.getNumberDay());
       }

class MyViewHolder extends RecyclerView.ViewHolder {
        //DECLARE ELEMENT OF MY VIEWHOLDER
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

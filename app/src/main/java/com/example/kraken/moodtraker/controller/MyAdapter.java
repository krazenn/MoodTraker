package com.example.kraken.moodtraker.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kraken.moodtraker.ListTicketComment;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.TicketComment;

class MyAdapter extends RecyclerView.Adapter {
    ListTicketComment listTicketComment;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.ticket_comment, parent, false);
        ViewHolder vh = new ViewHolder(view) {
        };

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TicketComment pair = listTicketComment.loadList().get(position);

        viewHolder.display(pair);
    }

    @Override
    public int getItemCount() {
        return listTicketComment.loadList().size();
    }
}

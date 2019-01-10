package com.example.kraken.moodtraker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.kraken.moodtraker.model.ListTicketComment;
import com.example.kraken.moodtraker.R;
import com.example.kraken.moodtraker.model.MyAdapter;
import com.example.kraken.moodtraker.model.TicketComment;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ListTicketComment listTicketComment;
    List<TicketComment> ticketCommentList;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Init Objet ListTicketComment
        listTicketComment = new ListTicketComment(this);

        if (listTicketComment.loadList() != null) {
            ticketCommentList = listTicketComment.loadList();
        }

        //Declare recyclerView
        mRecyclerView = findViewById(R.id.list_ticket_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set adapter from list ticket comment
        mAdapter = new MyAdapter(getApplicationContext(), listTicketComment.loadList());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);

        //Set recycler layout from adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}







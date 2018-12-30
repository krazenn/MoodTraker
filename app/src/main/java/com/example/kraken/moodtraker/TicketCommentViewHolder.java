package com.example.kraken.moodtraker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import static com.example.kraken.moodtraker.R.id.textViewDayAgo1;

public class TicketCommentViewHolder extends RecyclerView.ViewHolder {


    @BindView(textViewDayAgo1)
    private TextView textView;


    public TicketCommentViewHolder(View itemView) {

        super(itemView);

        ButterKnife.bind(this, itemView);

    }


    public void updateWithGithubUser(GithubUser githubUser) {

        this.textView.setText(githubUser.getLogin());

    }

}

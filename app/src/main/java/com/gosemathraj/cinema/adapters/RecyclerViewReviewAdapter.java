package com.gosemathraj.cinema.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.models.MovieReviews;

import java.util.List;

/**
 * Created by iamsparsh on 12/5/16.
 */
public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<RecyclerViewReviewAdapter.ReviewHolder> {

    private List<MovieReviews> movieReviews;
    private Context context;

    public RecyclerViewReviewAdapter(Context context, List<MovieReviews> movieReviews) {
        this.context = context;
        this.movieReviews = movieReviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_review_layout,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        holder.author.setText(movieReviews.get(position).getAuthor());
        holder.content.setText(movieReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        private TextView author;
        private TextView content;

        public ReviewHolder(View itemView) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.author);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}


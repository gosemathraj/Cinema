package com.gosemathraj.cinema.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.models.MovieTrailers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iamsparsh on 11/5/16.
 */
public class RecyclerViewTrailerAdapter extends RecyclerView.Adapter<RecyclerViewTrailerAdapter.TrailerViewHolder>{

    private List<MovieTrailers> mtrailers;
    private Context context;

    public RecyclerViewTrailerAdapter(Context context, List<MovieTrailers> mtrailers) {
        this.mtrailers = mtrailers;
        this.context = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_trailer_layout,parent,false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {

        String url = "http://img.youtube.com/vi/"+mtrailers.get(position).getKey()+"/mqdefault.jpg";
        Picasso.with(context).load(url).into(holder.imageview);

        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+mtrailers.get(position).getKey()));
                context.startActivity(Intent.createChooser(intent,"open with"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mtrailers.size();
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder{

        ImageView imageview;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            imageview = (ImageView) itemView.findViewById(R.id.recyclerview_trailer_image);
        }
    }

}

package com.gosemathraj.cinema.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.activities.MovieDetailsActivity;
import com.gosemathraj.cinema.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iamsparsh on 5/5/16.
 */
public class ViewPaderAdapter extends PagerAdapter {

    private InnerRecyclerViewCustomAdapter.OnAdapterItemClickedListener viewpagerlistener;
    private List<Movie> movieList;
    private String[] images;
    private Context context;
    private LayoutInflater inflater;


    public ViewPaderAdapter(String[] images, Context context, List<Movie> movieList) {
        this.images = images;
        this.movieList = movieList;
        this.context = context;
        viewpagerlistener = (InnerRecyclerViewCustomAdapter.OnAdapterItemClickedListener) context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final Movie m = movieList.get(position);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.viewpager_item_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_slider_imageview);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + images[position]).into(imageView);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie", m);
                context.startActivity(intent);*/
                viewpagerlistener.onInnerItemClicked(m);
            }
        });

        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (LinearLayout)object);
    }

}

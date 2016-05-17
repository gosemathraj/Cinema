package com.gosemathraj.cinema.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.activities.MovieDetailsActivity;
import com.gosemathraj.cinema.adapters.RecyclerViewReviewAdapter;
import com.gosemathraj.cinema.adapters.RecyclerViewTrailerAdapter;
import com.gosemathraj.cinema.database.DbHandler;
import com.gosemathraj.cinema.models.Movie;
import com.gosemathraj.cinema.models.MovieResult;
import com.gosemathraj.cinema.models.MovieReviews;
import com.gosemathraj.cinema.models.MovieReviewsAll;
import com.gosemathraj.cinema.models.MovieTrailers;
import com.gosemathraj.cinema.models.MovieTrailersAll;
import com.gosemathraj.cinema.utils.MovieApi;
import com.gosemathraj.cinema.utils.UrlConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamsparsh on 16/5/16.
 */
public class Fragment_Movies_Details extends Fragment {

    private Movie m;
    private MovieTrailersAll mtrailersAll;
    private MovieReviewsAll movieReviewsAll;
    private List<MovieTrailers> mtrailers;
    private List<MovieReviews> movieReviews;
    private TextView title;
    private TextView ratings;
    private TextView average;
    private TextView overview;
    private TextView release_date;
    private ImageView poster;
    private ImageView bg_poster;
    private RecyclerView trailers;
    private RecyclerView reviews;
    private DbHandler dbHandler;
    private MovieResult movieResult;
    private ArrayList<Movie> popularMovies = new ArrayList<>();

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_details,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_save);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        title = (TextView) view.findViewById(R.id.detail_title);
        ratings = (TextView)view.findViewById(R.id.detail_ratings);
        poster = (ImageView) view.findViewById(R.id.detail_poster);
        bg_poster = (ImageView) view.findViewById(R.id.backdrop_imageview);
        trailers = (RecyclerView) view.findViewById(R.id.recyclerview_trailers);
        reviews = (RecyclerView) view.findViewById(R.id.recyclerview_reviews);
        overview = (TextView) view.findViewById(R.id.detail_overview);
        release_date = (TextView) view.findViewById(R.id.detail_release_date);

        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        dbHandler = new DbHandler(getContext());

        m = getActivity().getIntent().getParcelableExtra("movie");

        if(m != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            updateLayout(m);
        }else{
            getPopularMovies();
        }

        return view;
    }

    public void updateLayout(Movie m){

        title.setText(m.getTitle());
        ratings.setText(m.getVote_average()+"/10");
        overview.setText(m.getOverview());
        release_date.setText(m.getRelease_date());
        collapsingToolbarLayout.setTitle(m.getTitle());

        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500"+m.getBackdrop_path()).into(bg_poster);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185" + m.getPoster_path()).into(poster);

        getTrailerInfo(m);
        getReviewInfo(m);

        if(dbHandler.isMoviePresent(m.getId()) == false){
            floatingActionButton.setImageResource(R.drawable.ic_favorite_outline);
        }else{
            floatingActionButton.setImageResource(R.drawable.ic_favorite);
        }
        setFabListener(m);
    }

    private void setFabListener(final Movie m) {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbHandler.isMoviePresent(m.getId()) == false) {

                    dbHandler.addMovie(m);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite);
                    Snackbar.make(v, "Movie Added to favourites", Snackbar.LENGTH_LONG).setAction("ACTION", null).show();
                } else {

                    dbHandler.deleteMovie(m);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_outline);
                    Snackbar.make(v, "Movie Deleted from favourites", Snackbar.LENGTH_LONG).setAction("ACTION", null).show();
                }
            }
        });
    }

    private void getTrailerInfo(Movie m){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieTrailersAll> call = movieApi.loadMovieTrailers("/3/movie/"+m.getId()+"/videos?api_key="+ UrlConstants.api_key);

        call.enqueue(new Callback<MovieTrailersAll>() {
            @Override
            public void onResponse(Call<MovieTrailersAll> call, Response<MovieTrailersAll> response) {

                mtrailersAll = response.body();
                mtrailers = mtrailersAll.getResults();

                trailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                trailers.setHasFixedSize(true);


                trailers.setNestedScrollingEnabled(false);
                trailers.setAdapter(new RecyclerViewTrailerAdapter(getContext(), mtrailers));

            }

            @Override
            public void onFailure(Call<MovieTrailersAll> call, Throwable t) {

            }
        });
    }

    private void getReviewInfo(Movie m) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieReviewsAll> call = movieApi.loadMovieReviews("/3/movie/" + m.getId() + "/reviews?api_key=" + UrlConstants.api_key);

        call.enqueue(new Callback<MovieReviewsAll>() {
            @Override
            public void onResponse(Call<MovieReviewsAll> call, Response<MovieReviewsAll> response) {

                movieReviewsAll = response.body();
                movieReviews = movieReviewsAll.getResults();

                reviews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                reviews.setHasFixedSize(true);
                reviews.setAdapter(new RecyclerViewReviewAdapter(getContext(),movieReviews));

            }

            @Override
            public void onFailure(Call<MovieReviewsAll> call, Throwable t) {

            }
        });

    }

    private void getPopularMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadPopularMovies("/3/discover/movie?sort_by=popularity.desc&api_key=" + UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                popularMovies = (ArrayList<Movie>) movieResult.getResult();
                Movie x = popularMovies.get(0);
                updateLayout(x);

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}

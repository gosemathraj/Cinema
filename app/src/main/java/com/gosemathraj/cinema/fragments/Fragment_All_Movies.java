package com.gosemathraj.cinema.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.adapters.InnerRecyclerViewCustomAdapter;
import com.gosemathraj.cinema.adapters.RecyclerViewCustomAdapter;
import com.gosemathraj.cinema.database.DbHandler;
import com.gosemathraj.cinema.models.Movie;
import com.gosemathraj.cinema.models.MovieResult;
import com.gosemathraj.cinema.utils.MovieApi;
import com.gosemathraj.cinema.utils.UrlConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamsparsh on 16/5/16.
 */
public class Fragment_All_Movies extends Fragment {

    private RecyclerView recyclerview;
    private MovieResult movieresult;
    private ArrayList<Movie> movies = new ArrayList<>();
    private String s;

    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_movies,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        recyclerview = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        s = this.getArguments().getString("variable");

        if (s.equals("Popular")) {

            appCompatActivity.getSupportActionBar().setTitle("Popular Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getPopularMovies();
            }

        }
        if (s.equals("Highest rated")) {

            appCompatActivity.getSupportActionBar().setTitle("Highest Rated Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getHighestRatedMovies();
            }
        }
        if (s.equals("favourites")) {

            appCompatActivity.getSupportActionBar().setTitle("Favourite Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getFavouriteMovies();
            }
        }

        if(s.equals("Upcoming")){

            appCompatActivity.getSupportActionBar().setTitle("Upcoming Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getUpComingMovies();
            }
        }

        if(s.equals("Highest Grossing")){

            appCompatActivity.getSupportActionBar().setTitle("Highest Grossing Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getHighestGrossingMovies();
            }
        }

        if(s.equals("latest release")){

            appCompatActivity.getSupportActionBar().setTitle("latest Release Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getThisYearReleaseMovies();
            }
        }

        if(s.equals("similar")){

            appCompatActivity.getSupportActionBar().setTitle("All Movies");
            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getMoviesList();
            }
        }


        return view;
    }

    private void getFavouriteMovies() {

        DbHandler dbHandler = new DbHandler(getContext());
        movies = (ArrayList<Movie>) dbHandler.getAllMovies();

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));

    }

    private void getHighestRatedMovies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestRatedMovies("/3/discover/movie?sort_by=vote_average.desc&api_key="+ UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });

    }

    public void getPopularMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadPopularMovies("/3/discover/movie?sort_by=popularity.desc&api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("movies", movies);
        outState.putString("state", s);
    }

    public void setRecyclerviewAdapter(){

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

            movies = savedInstanceState.getParcelableArrayList("movies");
            s = savedInstanceState.getString("state");
        }
    }

    private void getUpComingMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadUpcomingMovies("/3/movie/upcoming?api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getHighestGrossingMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestGrossingMovies("/3/discover/movie?sort_by=revenue.desc&api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getThisYearReleaseMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadThisYearReleaseMovies("/3/discover/movie?primary_release_year=2016&api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getMoviesList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadMovies("/3/discover/movie?api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext(), getActivity()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}

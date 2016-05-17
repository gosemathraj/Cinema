package com.gosemathraj.cinema.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.adapters.RecyclerViewCustomAdapter;
import com.gosemathraj.cinema.models.Movie;
import com.gosemathraj.cinema.models.MovieResult;
import com.gosemathraj.cinema.utils.MovieApi;
import com.gosemathraj.cinema.utils.UrlConstants;

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
public class Fragment_Movies_Home extends Fragment {

    private static boolean loaded;
    private static ArrayList<Movie> popularMovies = new ArrayList<>();
    private static ArrayList<Movie> highestRateMovies = new ArrayList<>();
    private static ArrayList<Movie> upcomingMovies = new ArrayList<>();
    private static ArrayList<Movie> highestGrossingMovies = new ArrayList<>();
    private static ArrayList<Movie> thisYearReleaseMovies = new ArrayList<>();
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static ArrayList<List<Movie>> allMovies = new ArrayList<>();
    private static String[] image_resources;
    private static MovieResult movieResult;
    private static String[] top_titles = {"Popular","Highest rated","Upcoming","Highest Grossing","latest release","similar"};

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies_home,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(savedInstanceState == null){
            if(loaded == false) {
                getPopularMovies();
            }else{
                allMovies.add(popularMovies);
                allMovies.add(highestRateMovies);
                allMovies.add(upcomingMovies);
                allMovies.add(highestGrossingMovies);
                allMovies.add(thisYearReleaseMovies);
                allMovies.add(movieList);

                String s2 = "hello";
                setsavedInstanceLayout();
            }
        }else if(loaded == true){
            allMovies.add(popularMovies);
            allMovies.add(highestRateMovies);
            allMovies.add(upcomingMovies);
            allMovies.add(highestGrossingMovies);
            allMovies.add(thisYearReleaseMovies);
            allMovies.add(movieList);

            String s2 = "hello";
            setsavedInstanceLayout();
        }else{
            getPopularMovies();
        }
        return view;
    }

    private void setsavedInstanceLayout() {

        setViewPagerData();
        recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(allMovies, getContext(), top_titles, image_resources,getActivity());
        recyclerView.setAdapter(recyclerViewCustomAdapter);
        recyclerViewCustomAdapter.notifyDataSetChanged();

    }

    private void setViewPagerData() {

        image_resources = new String[5];
        for(int i = 0;i < 5;i++){

            Movie m = movieList.get(i);
            image_resources[i] = m.getPoster_path();
        }
    }

    @Override
     public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("popularMovies",popularMovies);
        outState.putParcelableArrayList("highestRateMovies",highestRateMovies);
        outState.putParcelableArrayList("highestGrossingMovies",highestGrossingMovies);
        outState.putParcelableArrayList("upcomingMovies",upcomingMovies);
        outState.putParcelableArrayList("thisYearReleaseMovies",thisYearReleaseMovies);
        outState.putParcelableArrayList("movieList",movieList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            popularMovies = savedInstanceState.getParcelableArrayList("popularMovies");
            highestRateMovies = savedInstanceState.getParcelableArrayList("highestRateMovies");
            highestGrossingMovies = savedInstanceState.getParcelableArrayList("highestGrossingMovies");
            upcomingMovies = savedInstanceState.getParcelableArrayList("upcomingMovies");
            thisYearReleaseMovies = savedInstanceState.getParcelableArrayList("thisYearReleaseMovies");
            movieList = savedInstanceState.getParcelableArrayList("movieList");
        }

    }

    private void getPopularMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadPopularMovies("/3/discover/movie?sort_by=popularity.desc&api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                popularMovies = (ArrayList<Movie>) movieResult.getResult();

                allMovies.add(popularMovies);
                String s = "hello world";
                getHighestRateMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getHighestRateMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestRatedMovies("/3/discover/movie?sort_by=vote_average.desc&api_key="+UrlConstants.api_key);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                highestRateMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(highestRateMovies);
                String s ="hello world";
                getUpComingMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
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

                movieResult = response.body();
                upcomingMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(upcomingMovies);
                String s ="hello world";
                getHighestGrossingMovies();
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

                movieResult = response.body();
                highestGrossingMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(highestGrossingMovies);
                String s ="hello world";
                getThisYearReleaseMovies();
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

                movieResult = response.body();
                thisYearReleaseMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(thisYearReleaseMovies);
                String s ="hello world";
                getMoviesList();

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

                movieResult = response.body();
                movieList = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(movieList);

                loaded = true;

                int count = allMovies.size();
                String s = "hello";

                setViewPagerData();
                recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(allMovies, getContext(), top_titles, image_resources,getActivity());
                recyclerView.setAdapter(recyclerViewCustomAdapter);
                recyclerViewCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}

package com.gosemathraj.cinema.utils;

import com.gosemathraj.cinema.models.MovieResult;
import com.gosemathraj.cinema.models.MovieReviewsAll;
import com.gosemathraj.cinema.models.MovieTrailersAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * Created by iamsparsh on 4/5/16.
 */
public interface MovieApi {

    @GET
    Call<MovieResult> loadMovies(@Url String url);

    @GET
    Call<MovieTrailersAll> loadMovieTrailers(@Url String url);

    @GET
    Call<MovieReviewsAll> loadMovieReviews(@Url String url);

    @GET
    Call<MovieResult> loadPopularMovies(@Url String url);

    @GET
    Call<MovieResult> loadHighestRatedMovies(@Url String url);

    @GET
    Call<MovieResult> loadHighestGrossingMovies(@Url String url);

    @GET
    Call<MovieResult> loadThisYearReleaseMovies(@Url String url);

    @GET
    Call<MovieResult> loadUpcomingMovies(@Url String url);
}

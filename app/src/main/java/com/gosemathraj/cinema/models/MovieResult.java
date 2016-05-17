package com.gosemathraj.cinema.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iamsparsh on 5/5/16.
 */
public class MovieResult implements Parcelable{

    private List<Movie> results;

    protected MovieResult(Parcel in) {
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };

    public List<Movie> getResult() {
        return results;
    }

    public void setResult(List<Movie> result) {
        this.results = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
}

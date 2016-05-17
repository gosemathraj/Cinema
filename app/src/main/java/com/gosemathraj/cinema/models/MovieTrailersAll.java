package com.gosemathraj.cinema.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iamsparsh on 11/5/16.
 */
public class MovieTrailersAll implements Parcelable {

    private String id;
    private List<MovieTrailers> results;

    public MovieTrailersAll(String id, List<MovieTrailers> results) {
        this.id = id;
        this.results = results;
    }

    protected MovieTrailersAll(Parcel in) {
        id = in.readString();
        results = in.createTypedArrayList(MovieTrailers.CREATOR);
    }

    public static final Creator<MovieTrailersAll> CREATOR = new Creator<MovieTrailersAll>() {
        @Override
        public MovieTrailersAll createFromParcel(Parcel in) {
            return new MovieTrailersAll(in);
        }

        @Override
        public MovieTrailersAll[] newArray(int size) {
            return new MovieTrailersAll[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MovieTrailers> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailers> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeTypedList(results);
    }
}
